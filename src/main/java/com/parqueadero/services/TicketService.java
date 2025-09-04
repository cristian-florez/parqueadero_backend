package com.parqueadero.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parqueadero.dtos.cierreTurno.TicketCierreTurno;
import com.parqueadero.dtos.tickets.TicketEntradaRequest;
import com.parqueadero.dtos.tickets.TicketMensualidadRequest;
import com.parqueadero.dtos.tickets.TicketSalidaRequest;
import com.parqueadero.models.Pago;
import com.parqueadero.models.Ticket;
import com.parqueadero.models.Usuario;
import com.parqueadero.models.Vehiculo;
import com.parqueadero.repositories.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private UsuarioService usuarioService;

    public Page<Ticket> buscarTodos(Pageable pageable, String codigo, String placa, String tipo, String usuarioRecibio,
            String usuarioEntrego, LocalDateTime fechaInicio, LocalDateTime fechaFin, Boolean pagado) {
        Pageable pageableOrdenado = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("fechaHoraEntrada").descending());

        Specification<Ticket> spec = TicketSpecification.hasCodigo(codigo)
                .and(TicketSpecification.hasPlaca(placa))
                .and(TicketSpecification.hasTipo(tipo))
                .and(TicketSpecification.hasUsuarioRecibio(usuarioRecibio))
                .and(TicketSpecification.hasUsuarioEntrego(usuarioEntrego))
                .and(TicketSpecification.isPagado(pagado))
                .and(TicketSpecification.fechaEntradaBetween(fechaInicio, fechaFin));

        return ticketRepository.findAll(spec, pageableOrdenado);
    }

    public Optional<Ticket> buscarPorId(Long id) {
        return ticketRepository.findById(id);
    }

    public void eliminarPorId(Long id) {
        ticketRepository.deleteById(id);
    }

    // como este metodo se usa para cuando hay salida de vehiculo
    // tenemos que validar que no exista un pago
    public Ticket buscarTicketCodigo(String codigoBarras) {
        if (codigoBarras != null && !codigoBarras.isEmpty()) {
            Ticket ticket = ticketRepository.findByCodigo(codigoBarras);

            if (ticket != null) {

                if (!pagoService.obtenerEstadoPago(ticket.getPago().getId())) {
                    return ticket;
                }
            }
        }
        return null;
    }

    private String generarCodigo(String placa, LocalDateTime fechaEntrada) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede ser nula o vacía para generar el código.");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fechaFormateada = fechaEntrada.format(formatter);
        return placa.toUpperCase() + fechaFormateada;
    }

    // logica para crear el ticket cuando se entra un vehiculo
    @Transactional
    public Ticket ticketEntradaVehiculo(TicketEntradaRequest ticketRequest) {

        Usuario usuarioRecibio = usuarioService.buscarPorId(ticketRequest.getUsuarioRecibioId());

        Vehiculo vehiculo = vehiculoService.crearVehiculo(ticketRequest.getPlaca(), ticketRequest.getTipoVehiculo());

        Ticket ticket = new Ticket();

        ticket.setCodigo(generarCodigo(vehiculo.getPlaca(), LocalDateTime.now()));
        ticket.setFechaHoraEntrada(LocalDateTime.now());
        ticket.setUsuarioRecibio(usuarioRecibio);
        ticket.setVehiculo(vehiculo);

        Pago pago = new Pago(false, 0, null, ticket);

        ticket.setPago(pago);

        return ticketRepository.save(ticket);
    }

    // logica cuando un vehiculo va a salir
    @Transactional
    public Ticket actualizarTicketSalida(TicketSalidaRequest ticketRequest) {
        Ticket ticketExistente = buscarTicketCodigo(ticketRequest.getCodigo());

        if (ticketExistente == null) {
            throw new RuntimeException("El ticket con codigo: " + ticketRequest.getCodigo() + " no encontrado");
        }

        LocalDateTime salida = LocalDateTime.now();

        ticketExistente.setFechaHoraSalida(salida);
        ticketExistente.setUsuarioEntrego(usuarioService.buscarPorId(ticketRequest.getIdUsuarioLogueado()));
        ticketExistente.getPago().setEstado(true);
        ticketExistente.getPago().setFechaHora(salida);
        ticketExistente.getPago().setTotal(pagoService.calcularTotal(
                ticketRequest.getCodigo(),
                ticketExistente.getVehiculo().getTipo(),
                ticketExistente.getFechaHoraEntrada(),
                salida));

        return ticketRepository.save(ticketExistente);
    }

    // logica para manejar mensualidad
    @Transactional
    public Ticket ticketMensualidad(TicketMensualidadRequest ticketRequest) {

        Usuario usuario = usuarioService.buscarPorId(ticketRequest.getUsuarioId());

        Vehiculo vehiculo = vehiculoService.crearVehiculo(ticketRequest.getPlaca(), ticketRequest.getTipoVehiculo());

        Ticket ticket = new Ticket();

        ticket.setCodigo(generarCodigo(vehiculo.getPlaca(), LocalDateTime.now()));
        ticket.setFechaHoraEntrada(ticketRequest.getFechaHoraEntrada());
        ticket.setFechaHoraSalida(ticketRequest.getFechaHoraEntrada().plusDays(ticketRequest.getDias()));
        ticket.setUsuarioRecibio(usuario);
        ticket.setUsuarioEntrego(usuario);
        ticket.setVehiculo(vehiculo);

        Pago pago = new Pago(true, ticketRequest.getTotal(), LocalDateTime.now(), ticket);

        ticket.setPago(pago);

        return ticketRepository.save(ticket);
    }

    //aca se genera los datos para pasarselo despues al servidor de cierre turno
    public TicketCierreTurno generarDatosCierre(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {

        TicketCierreTurno cierreTurno = new TicketCierreTurno();

        cierreTurno.setFechaInicio(fechaInicio);
        cierreTurno.setFechaCierre(fechaFinal);
        cierreTurno.setListaVehiculosEntrantes(ticketRepository.vehiculosQueEntraron(fechaInicio, fechaFinal));
        cierreTurno.setListaVehiculosSalientes(ticketRepository.vehiculosQueSalieron(fechaInicio, fechaFinal));

        Integer totalAPagar = ticketRepository.totalCierreTurno(fechaInicio, fechaFinal, "MENSUALIDAD");
        cierreTurno.setTotalAPagar(totalAPagar != null ? totalAPagar : 0);

        cierreTurno.setVehiculosEnParqueadero(ticketRepository.findVehiculosEnParqueadero());

        cierreTurno.setListaTiposVehiculosEntrantes(
                ticketRepository.findTotalVehiculosEntrantes(fechaInicio, fechaFinal));
        cierreTurno.setListaTiposVehiculosSalientes(
                ticketRepository.findTotalVehiculosSalientes(fechaInicio, fechaFinal));
        cierreTurno.setListaTiposVehiculosParqueadero(ticketRepository.findTotalVehiculosParqueadero());

        return cierreTurno;
    }
}
