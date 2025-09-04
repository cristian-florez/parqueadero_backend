package com.parqueadero.controllers;

import com.parqueadero.dtos.cierreTurno.CierreReimpresionResponse;
import com.parqueadero.dtos.cierreTurno.TicketCierreResponse;
import com.parqueadero.dtos.cierreTurno.TicketCierreTurno;
import com.parqueadero.mappers.CierreTurnoMapper;
import com.parqueadero.models.CierreTurno;
import com.parqueadero.services.CierreTurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/cierre")
public class CierreTurnoController {

    @Autowired
    private CierreTurnoService cierreTurnoService;

    @Autowired
    private CierreTurnoMapper cierreTurnoMapper;

    // Crear y guardar un cierre de turno
    @PostMapping("/{idUsuario}")
    public ResponseEntity<?> crearCierre(@PathVariable Long idUsuario) {
        try {
            TicketCierreTurno ticket = cierreTurnoService.crearYGuardarCierre(idUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear cierre: " + e.getMessage());
        }
    }

    // Listar todos los cierres (con paginación y filtros opcionales)
    @GetMapping
    public ResponseEntity<?> obtenerTodos(
            Pageable pageable,
            @RequestParam(required = false) LocalDateTime inicio,
            @RequestParam(required = false) LocalDateTime fin,
            @RequestParam(required = false) String usuario) {

        try {
            Page<CierreTurno> page = cierreTurnoService.obtenerTodosLosCierres(pageable, inicio, fin, usuario);

            if (page.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay cierres disponibles");
            }

            Page<TicketCierreResponse> response = page.map(cierreTurnoMapper::toResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener cierres: " + e.getMessage());
        }
    }

    // Obtener un cierre específico (para reimpresión del ticket)
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCierrePorId(@PathVariable Long id) {
        try {
            CierreTurno cierre = cierreTurnoService.obtenerCierrePorId(id);
            CierreReimpresionResponse response = cierreTurnoMapper.toReimpresionResponse(cierre);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener cierre: " + e.getMessage());
        }
    }
}
