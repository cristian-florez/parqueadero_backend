package com.parqueadero.controllers;

import com.parqueadero.models.CierreTurno;
import com.parqueadero.models.DTOS.TicketCierreTurno;
import com.parqueadero.services.CierreTurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/cierre-turno")
public class CierreTurnoController {

    @Autowired
    private CierreTurnoService cierreTurnoService;

    @PostMapping
    public ResponseEntity<TicketCierreTurno> crearYGuardarCierre(
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
        @RequestParam("usuario") String nombreUsuario) {

        TicketCierreTurno dto = cierreTurnoService.crearYGuardarCierre(inicio, fin, nombreUsuario);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<CierreTurno>> obtenerTodosLosCierres(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "fechaCreacion") String sortBy,
        @RequestParam(defaultValue = "DESC") String sortDirection,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
        @RequestParam(required = false) String nombreUsuario) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(cierreTurnoService.obtenerTodosLosCierres(pageable, inicio, fin, nombreUsuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CierreTurno> obtenerCierrePorId(@PathVariable Long id) {
        return cierreTurnoService.obtenerCierrePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
