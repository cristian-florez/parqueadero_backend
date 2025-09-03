package com.parqueadero.services;

import com.parqueadero.models.Pago;
import com.parqueadero.models.Ticket;
import com.parqueadero.models.DTOS.TicketCierreTurno;
import com.parqueadero.repositories.TicketRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.parqueadero.models.DTOS.TicketEntrada;
import com.parqueadero.models.Vehiculo;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private VehiculoService vehiculoService;

    public Page<Ticket> buscarTodos(Pageable pageable, String codigo, String placa, String tipo, String usuarioRecibio, String usuarioEntrego, LocalDateTime fechaInicio, LocalDateTime fechaFin, Boolean pagado) {
        Pageable pageableOrdenado = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by("fechaHoraEntrada").descending()
        );

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

    public Ticket buscarTicketCodigo(String codigoBarras) {
        if (codigoBarras != null && !codigoBarras.isEmpty()) {
            return ticketRepository.findByCodigoBarrasQRAndPagado(codigoBarras, false);
        }
        return null;
    }

    public List<Ticket> totalVehiculosParqueadero(Boolean estado) {
        return ticketRepository.findByPagado(estado);
    }

    public Double totalAPagar(LocalDateTime fechaIncial, LocalDateTime fechaFinal) {
        return ticketRepository.findTotal(fechaIncial, fechaFinal);
    }

    public String generarCodigo(String placa, LocalDateTime fechaEntrada) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede ser nula o vacía para generar el código.");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fechaFormateada = fechaEntrada.format(formatter);
        return placa.toUpperCase() + fechaFormateada;
    }

    public TicketCierreTurno ticketCierreTurno(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        LocalDateTime fechaCierre = (fechaFin != null) ? fechaFin : LocalDateTime.now();
        TicketCierreTurno cierreTurno = new TicketCierreTurno();

        cierreTurno.setTotalVehiculosQueEntraron(ticketRepository.vehiculosQueEntraron(fechaInicio, fechaCierre));
        cierreTurno.setTotalVehiculosQueSalieron(ticketRepository.vehiculosQueSalieron(fechaInicio, fechaCierre));

        Double totalAPagar = ticketRepository.totalCierreTurno(fechaInicio, fechaCierre, "MENSUALIDAD");
        cierreTurno.setTotalAPagar(totalAPagar != null ? totalAPagar : 0.0);

        cierreTurno.setVehiculosEnParqueadero(ticketRepository.findVehiculosSinSalida());

        cierreTurno.setTipoVehiculosEntrantes(ticketRepository.findTotalVehiculosEntrantes(fechaInicio, fechaCierre));
        cierreTurno.setTipoVehiculosSaliente(ticketRepository.findTotalVehiculosSalientes(fechaInicio, fechaCierre));
        cierreTurno.setTipoVehiculosParqueadero(ticketRepository.findTotalVehiculosParqueadero());

        return cierreTurno;
    }

    public Ticket crearTicket(TicketEntrada ticketEntrada) {
        Vehiculo vehiculo = vehiculoService.findOrCreateVehiculo(ticketEntrada.placa(), ticketEntrada.tipoVehiculo());

        Ticket ticket = new Ticket();
        ticket.setVehiculo(vehiculo);
        ticket.setUsuarioRecibio(ticketEntrada.usuarioRecibio());

        LocalDateTime fechaEntrada = (ticketEntrada.fechaHoraEntrada() != null) ? ticketEntrada.fechaHoraEntrada() : LocalDateTime.now();
        ticket.setFechaHoraEntrada(fechaEntrada);
        ticket.setCodigoBarrasQR(generarCodigo(vehiculo.getPlaca(), fechaEntrada));

        // Lógica para el pago por adelantado
        if (ticketEntrada.pagado() != null && ticketEntrada.pagado() && ticketEntrada.total() != null && ticketEntrada.total() > 0) {
            Pago nuevoPago = new Pago();
            nuevoPago.setTotal(ticketEntrada.total());
            nuevoPago.setFechaHora(LocalDateTime.now());
            nuevoPago.setMetodoPago("PAGO_ADELANTADO");
            ticket.setPago(nuevoPago);
            ticket.setPagado(true);
        } else {
            ticket.setPagado(false);
        }

        if (ticket.getPago() != null && ticket.getPago().getId() == null) {
            ticket.getPago().setTicket(ticket);
        }
        return ticketRepository.save(ticket);
    }



    public Optional<Ticket> actualizarTicketSalida(String codigo, Ticket ticketDatos) {
        Ticket ticketExistente = buscarTicketCodigo(codigo);
        if (ticketExistente == null) {
            return Optional.empty();
        }

        LocalDateTime salida = LocalDateTime.now();
        ticketExistente.setFechaHoraSalida(salida);
        ticketExistente.setPagado(true);
        ticketExistente.setUsuarioEntrego(ticketDatos.getUsuarioEntrego());
        ticketExistente.setPago(pagoService.calcularTotal(
            codigo,
            ticketExistente.getVehiculo().getTipo(),
            ticketExistente.getFechaHoraEntrada(),
            salida
        ));

        if (ticketExistente.getPago() != null && ticketExistente.getPago().getId() == null) {
            ticketExistente.getPago().setTicket(ticketExistente);
        }
        Ticket ticketActualizado = ticketRepository.save(ticketExistente);
        return Optional.of(ticketActualizado);
    }
}
