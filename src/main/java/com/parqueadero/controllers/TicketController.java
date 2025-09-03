package com.parqueadero.controllers;

import com.parqueadero.models.Ticket;
import com.parqueadero.models.DTOS.TicketEntrada;
import com.parqueadero.models.DTOS.TicketCierreTurno;
import com.parqueadero.services.TicketService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String codigo,
        @RequestParam(required = false) String placa,
        @RequestParam(required = false) String tipo,
        @RequestParam(required = false) String usuarioRecibio,
        @RequestParam(required = false) String usuarioEntrego,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
        @RequestParam(required = false) Boolean pagado) {
            Pageable pageable = PageRequest.of(page, size);
            return ticketService.buscarTodos(pageable, codigo, placa, tipo, usuarioRecibio, usuarioEntrego, fechaInicio, fechaFin, pagado);
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
    public Ticket crearTicket(@RequestBody TicketEntrada ticket) {
        return ticketService.crearTicket(ticket);
    }

    @PutMapping("/salida/{codigo}")
    public ResponseEntity<Ticket> actualizarTicket(@PathVariable String codigo, @RequestBody Ticket ticket) {
        return ticketService.actualizarTicketSalida(codigo, ticket)
                .map(ResponseEntity::ok)
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

    @GetMapping("/cierre-turno")
    public ResponseEntity<TicketCierreTurno> obtenerDatosImpresion(
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
            return ResponseEntity.ok(ticketService.ticketCierreTurno(inicio, fin));
    }


}