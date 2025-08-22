package com.parqueadero.controllers;

import com.parqueadero.models.Ticket;
import com.parqueadero.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public Page<Ticket> obtenerTodosLosTickets(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
            Pageable pageable = PageRequest.of(page, size);
            return ticketService.buscarTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerTicketPorId(@PathVariable Long id) {
        return ticketService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ticket crearTicket(@RequestBody Ticket ticket) {
        return ticketService.guardar(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> actualizarTicket(@PathVariable Long id, @RequestBody Ticket ticketDetails) {
        return ticketService.buscarPorId(id)
                .map(ticket -> {
                    ticket.setCodigoBarrasQR(ticketDetails.getCodigoBarrasQR());
                    ticket.setFechaHoraEntrada(ticketDetails.getFechaHoraEntrada());
                    ticket.setFechaHoraSalida(ticketDetails.getFechaHoraSalida());
                    ticket.setPagado(ticketDetails.getPagado());
                    ticket.setUsuarioRecibio(ticketDetails.getUsuarioRecibio());
                    ticket.setUsuarioEntrego(ticketDetails.getUsuarioEntrego());
                    ticket.setVehiculo(ticketDetails.getVehiculo());
                    return ResponseEntity.ok(ticketService.guardar(ticket));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarTicket(@PathVariable Long id) {
        return ticketService.buscarPorId(id)
                .map(ticket -> {
                    ticketService.eliminarPorId(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}