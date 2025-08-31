package com.parqueadero.services;

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

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

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

    public Ticket guardar(Ticket ticket) {
        // If a Pago object is present and its ID is null (meaning it's a new Pago)
        // and the ticket is not yet persisted (ticket.getId() == null),
        // set the ticket on the pago object before saving.
        if (ticket.getPago() != null && ticket.getPago().getId() == null) {
            ticket.getPago().setTicket(ticket); // Set the owning side of the relationship
        }
        return ticketRepository.save(ticket);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fechaFormateada = fechaEntrada.format(formatter);
        String codigo = placa.toUpperCase() + fechaFormateada;
        return codigo;
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
}