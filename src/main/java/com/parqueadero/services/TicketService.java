package com.parqueadero.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.parqueadero.dtos.cierreTurno.DetalleParqueaderoCierre;
import com.parqueadero.dtos.vehiculos.TotalVehiculosDTO;
import com.parqueadero.dtos.vehiculos.VehiculoCierreDTO;
import com.parqueadero.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parqueadero.dtos.cierreTurno.TicketCierreTurnoResponse;
import com.parqueadero.dtos.tickets.TicketEntradaRequest;
import com.parqueadero.dtos.tickets.TicketMensualidadRequest;
import com.parqueadero.dtos.tickets.TicketSalidaRequest;
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

    @Autowired
    private ParqueaderoService parqueaderoService;

    public Page<Ticket> buscarTodos(Pageable pageable, String codigo, String placa, String tipo, String usuarioRecibio,
                                    String usuarioEntrego, LocalDateTime fechaInicio, LocalDateTime fechaFin, Boolean pagado, String parqueadero) {

        Specification<Ticket> spec = TicketSpecification.hasCodigo(codigo)
                .and(TicketSpecification.hasPlaca(placa))
                .and(TicketSpecification.hasTipo(tipo))
                .and(TicketSpecification.hasUsuarioRecibio(usuarioRecibio))
                .and(TicketSpecification.hasUsuarioEntrego(usuarioEntrego))
                .and(TicketSpecification.hasParqueadero(parqueadero))
                .and(TicketSpecification.isPagado(pagado))
                .and(TicketSpecification.fechaEntradaBetween(fechaInicio, fechaFin));

        return ticketRepository.findAll(spec, pageable);
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
                //este if es utilizado para que si el estado ya es verdadero no me devuelva el ticket
                //ya que sobrescribiria datos
                if (!ticket.getPago().getEstado()) {
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

        Parqueadero parqueadero = parqueaderoService.buscarOCrear(ticketRequest.getParqueadero());

        Ticket ticket = new Ticket();

        ticket.setCodigo(generarCodigo(vehiculo.getPlaca(), LocalDateTime.now()));
        ticket.setFechaHoraEntrada(LocalDateTime.now());
        ticket.setUsuarioRecibio(usuarioRecibio);
        ticket.setVehiculo(vehiculo);
        ticket.setParqueadero(parqueadero);

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
        ticketExistente.getPago().setTotal(pagoService.calcularTotal(ticketRequest.getCodigo()));

        return ticketRepository.save(ticketExistente);
    }

    // logica para manejar mensualidad
    @Transactional
    public Ticket ticketMensualidad(TicketMensualidadRequest ticketRequest) {

        Usuario usuario = usuarioService.buscarPorId(ticketRequest.getUsuarioId());

        Vehiculo vehiculo = vehiculoService.crearVehiculo(ticketRequest.getPlaca(), ticketRequest.getTipoVehiculo());

        Parqueadero parqueadero = parqueaderoService.buscarOCrear(ticketRequest.getParqueadero());

        Ticket ticket = new Ticket();

        ticket.setCodigo("MENSUALIDAD" + generarCodigo(vehiculo.getPlaca(), LocalDateTime.now()));
        ticket.setFechaHoraEntrada(ticketRequest.getFechaHoraEntrada());
        ticket.setFechaHoraSalida(ticketRequest.getFechaHoraEntrada().plusDays(ticketRequest.getDias()));
        ticket.setUsuarioRecibio(usuario);
        ticket.setUsuarioEntrego(usuario);
        ticket.setVehiculo(vehiculo);
        ticket.setParqueadero(parqueadero);

        Pago pago = new Pago(true, ticketRequest.getTotal(), LocalDateTime.now(), ticket);

        ticket.setPago(pago);

        return ticketRepository.save(ticket);
    }

    private List<TotalVehiculosDTO> calcularTotalesPorTipo(List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            return new ArrayList<>();
        }

        return tickets.stream()
                .map(Ticket::getVehiculo)
                .collect(Collectors.groupingBy(Vehiculo::getTipo, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new TotalVehiculosDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    //aca se genera los datos para pasarselo despues al servicio de cierre turno
    public TicketCierreTurnoResponse generarDatosCierre(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {

        List<Parqueadero> todosLosParqueaderos = parqueaderoService.buscarTodos();
        List<Ticket> ticketsCierre = ticketRepository.findTicketsParaCierre(fechaInicio, fechaFinal);
        List<Ticket> ticketsParqueadero = ticketRepository.findTicketsAbiertos();


        TicketCierreTurnoResponse cierreTurno = new TicketCierreTurnoResponse();
        cierreTurno.setFechaInicio(fechaInicio);
        cierreTurno.setFechaCierre(fechaFinal);

        Map<String, DetalleParqueaderoCierre> mapaDetalles = new HashMap<>();

        for (Parqueadero parqueadero : todosLosParqueaderos) {

            if (parqueadero == null) {
                System.out.println("ADVERTENCIA: Se encontró un objeto Parqueadero nulo en la lista. Saltando esta iteración.");
                continue;
            }
            if (parqueadero.getNombre() == null) {
                System.out.println("ADVERTENCIA: El parqueadero con ID: " + parqueadero.getId() + " tiene un nombre nulo. Saltando la creación de detalles para este parqueadero.");
                continue;
            }

            DetalleParqueaderoCierre detalle = new DetalleParqueaderoCierre();
            Long parqueaderoId = parqueadero.getId();

            // --- A. Filtrar listas para este parqueadero ---
            // Vehículos que entraron en el turno
            List<Ticket> entrantes = ticketsCierre.stream()
                    .filter(t -> t.getParqueadero().getId().equals(parqueaderoId) &&
                            t.getFechaHoraEntrada().isAfter(fechaInicio.minusNanos(1)) &&
                            t.getFechaHoraEntrada().isBefore(fechaFinal.plusNanos(1)))
                    .collect(Collectors.toList());

            // Vehículos que salieron en el turno
            List<Ticket> salientes = ticketsCierre.stream()
                    .filter(t -> t.getParqueadero().getId().equals(parqueaderoId) &&
                            t.getFechaHoraSalida() != null &&
                            t.getFechaHoraSalida().isAfter(fechaInicio.minusNanos(1)) &&
                            t.getFechaHoraSalida().isBefore(fechaFinal.plusNanos(1)))
                    .collect(Collectors.toList());

            // Vehículos que siguen adentro (usando la segunda lista)
            List<Ticket> dentroParqueadero = ticketsParqueadero.stream()
                    .filter(t -> t.getParqueadero().getId().equals(parqueaderoId))
                    .collect(Collectors.toList());

            // Vehículos en mensualidad
            List<Ticket> mensualidad = ticketsCierre.stream()
                    .filter(t -> t.getParqueadero().getId().equals(parqueaderoId) &&
                            t.getCodigo().contains("MENSUALIDAD") &&
                            t.getPago().getFechaHora().isAfter(fechaInicio.minusNanos(1)) &&
                            t.getPago().getFechaHora().isBefore(fechaFinal.plusNanos(1)))
                    .collect(Collectors.toList());

            // --- B. Asignar listas de vehículos al DTO de detalle ---
            detalle.setListaVehiculosEntrantes(
                    entrantes.stream().map(Ticket::getVehiculo).collect(Collectors.toList())
            );
            detalle.setListaVehiculosSalientes(
                    salientes.stream().map(ticket -> new VehiculoCierreDTO(
                            ticket.getVehiculo().getPlaca(),
                            ticket.getVehiculo().getTipo(),
                            ticket.getPago().getTotal()
                    )).collect(Collectors.toList())
            );
            detalle.setVehiculosEnParqueadero(
                    dentroParqueadero.stream().map(Ticket::getVehiculo).collect(Collectors.toList())
            );
            detalle.setVehiculosMensualidad(
                    mensualidad.stream().map(ticket -> new VehiculoCierreDTO(
                            ticket.getVehiculo().getPlaca(),
                            ticket.getVehiculo().getTipo(),
                            ticket.getPago().getTotal()
                    )).collect(Collectors.toList())
            );

            // --- C. Calcular el total pagado en el turno para este parqueadero ---
            int totalPagado = ticketsCierre.stream()
                    .filter(t -> t.getParqueadero().getId().equals(parqueaderoId) &&
                            t.getPago() != null && t.getPago().getFechaHora() != null &&
                            t.getPago().getFechaHora().isAfter(fechaInicio.minusNanos(1)) &&
                            t.getPago().getFechaHora().isBefore(fechaFinal.plusNanos(1)))
                    .mapToInt(t -> t.getPago().getTotal())
                    .sum();
            detalle.setTotalAPagar(totalPagado);

            // --- D. Calcular los totales por tipo de vehículo ---
            detalle.setListaTiposVehiculosEntrantes(calcularTotalesPorTipo(entrantes));

            detalle.setListaTiposVehiculosSalientes(calcularTotalesPorTipo(salientes));

            detalle.setListaTiposVehiculosParqueadero(calcularTotalesPorTipo(dentroParqueadero));

            // --- E. Añadir el detalle completado al mapa principal ---
            System.out.println("Procesando parqueadero: " + parqueadero.getNombre());
            mapaDetalles.put(parqueadero.getNombre(), detalle);
        }

        cierreTurno.setDetallesPorParqueadero(mapaDetalles);

        int totalGeneral = mapaDetalles.values().stream()
                .mapToInt(detalle -> detalle.getTotalAPagar() != null ? detalle.getTotalAPagar() : 0)
                .sum();
        cierreTurno.setTotal(totalGeneral);

        return cierreTurno;

    }

}

