package com.parqueadero.controllers;

import com.parqueadero.models.Pago;
import com.parqueadero.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> obtenerTodosLosPagos() {
        return pagoService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPagoPorId(@PathVariable Long id) {
        return pagoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pago crearPago(@RequestBody Pago pago) {
        return pagoService.guardar(pago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @RequestBody Pago pagoDetails) {
        return pagoService.buscarPorId(id)
                .map(pago -> {
                    pago.setTotal(pagoDetails.getTotal());
                    pago.setFechaHora(pagoDetails.getFechaHora());
                    pago.setTicket(pagoDetails.getTicket());
                    return ResponseEntity.ok(pagoService.guardar(pago));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarPago(@PathVariable Long id) {
        return pagoService.buscarPorId(id)
                .map(pago -> {
                    pagoService.eliminarPorId(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}