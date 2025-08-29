package com.parqueadero.controllers;

import com.parqueadero.models.CierreTurno;
import com.parqueadero.models.DTOS.TicketCierreTurno;
import com.parqueadero.services.CierreTurnoService;
import com.parqueadero.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cierre-turno")
public class CierreTurnoController {

    @Autowired
    private CierreTurnoService cierreTurnoService;

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketCierreTurno> crearYGuardarCierre(
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
        @RequestParam("usuario") String nombreUsuario) {

        // 1. Calcular el DTO como antes
        TicketCierreTurno dto = ticketService.ticketCierreTurno(inicio, fin);

        // 2. Mapear el DTO a la nueva entidad CierreTurno
        CierreTurno nuevoCierre = new CierreTurno();
        nuevoCierre.setFechaCreacion(LocalDateTime.now());
        nuevoCierre.setFechaInicioTurno(inicio);
        nuevoCierre.setFechaFinTurno(fin != null ? fin : LocalDateTime.now());
        nuevoCierre.setNombreUsuario(nombreUsuario);
        nuevoCierre.setTotalIngresos(dto.getTotalAPagar());

        if (dto.getTotalVehiculosQueEntraron() != null) {
            nuevoCierre.setTotalVehiculosEntraron(dto.getTotalVehiculosQueEntraron().size());
        }
        if (dto.getTotalVehiculosQueSalieron() != null) {
            nuevoCierre.setTotalVehiculosSalieron(dto.getTotalVehiculosQueSalieron().size());
        }
        if (dto.getVehiculosEnParqueadero() != null) {
            nuevoCierre.setVehiculosRestantes(dto.getVehiculosEnParqueadero().size());
        }

        // Mapear detalles a String
        nuevoCierre.setDetalleEntrantes(formatDetalle(dto.getTipoVehiculosEntrantes()));
        nuevoCierre.setDetalleSalientes(formatDetalle(dto.getTipoVehiculosSaliente()));
        nuevoCierre.setDetalleRestantes(formatDetalle(dto.getTipoVehiculosParqueadero()));

        // 3. Guardar la nueva entidad
        cierreTurnoService.guardarCierreTurno(nuevoCierre);

        // 4. Devolver el DTO original al frontend
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CierreTurno>> obtenerTodosLosCierres() {
        return ResponseEntity.ok(cierreTurnoService.obtenerTodosLosCierres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CierreTurno> obtenerCierrePorId(@PathVariable Long id) {
        return cierreTurnoService.obtenerCierrePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Helper para formatear los detalles
    private String formatDetalle(List<Object> detalle) {
        if (detalle == null || detalle.isEmpty()) {
            return "";
        }
        return detalle.stream()
                .map(item -> {
                    if (item instanceof Object[]) {
                        Object[] array = (Object[]) item;
                        if (array.length >= 2) {
                            return array[0].toString() + ": " + array[1].toString();
                        }
                    }
                    return item.toString();
                })
                .collect(Collectors.joining(", "));
    }
}
