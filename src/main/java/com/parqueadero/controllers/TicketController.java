package com.parqueadero.controllers;

import com.parqueadero.models.Ticket;
import com.parqueadero.services.PagoService;
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

    @Autowired
    private PagoService pagoService;


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

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Ticket> obtenerTicketPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(ticketService.buscarTicketCodigo(codigo));

    }

    @PostMapping
    public Ticket crearTicket(@RequestBody Ticket ticket) {
        ticket.setPagado(false);
        ticket.setFechaHoraEntrada(java.time.LocalDateTime.now());
        return ticketService.guardar(ticket);
    }

    @PutMapping("/salida/{codigo}")
    public ResponseEntity<Ticket> actualizarTicket(@PathVariable String codigo, @RequestBody Ticket ticket) {
        Ticket ticketExistente = ticketService.buscarTicketCodigo(codigo);

        if (ticketExistente == null) {
            return ResponseEntity.notFound().build();
        }

        ticketExistente.setFechaHoraSalida(java.time.LocalDateTime.now());
        ticketExistente.setPagado(true);
        ticketExistente.setUsuarioEntrego(ticket.getUsuarioEntrego());
        ticketExistente.setPago(pagoService.calcularTotal(
            codigo,
            ticketExistente.getVehiculo().getTipo(),
             ticketExistente.getFechaHoraEntrada(),
             java.time.LocalDateTime.now()));


        Ticket actualizado = ticketService.guardar(ticketExistente);
        return ResponseEntity.ok(actualizado);
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