package com.parqueadero.controllers;

import com.parqueadero.models.Tarifa;
import com.parqueadero.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @GetMapping
    public List<Tarifa> obtenerTodasLasTarifas() {
        return tarifaService.buscarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> obtenerTarifaPorId(@PathVariable Long id) {
        return tarifaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tarifa crearTarifa(@RequestBody Tarifa tarifa) {
        return tarifaService.guardar(tarifa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> actualizarTarifa(@PathVariable Long id, @RequestBody Tarifa tarifaDetails) {
        return tarifaService.buscarPorId(id)
                .map(tarifa -> {
                    tarifa.setTipoVehiculo(tarifaDetails.getTipoVehiculo());
                    tarifa.setPrecioDia(tarifaDetails.getPrecioDia());
                    return ResponseEntity.ok(tarifaService.guardar(tarifa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarTarifa(@PathVariable Long id) {
        return tarifaService.buscarPorId(id)
                .map(tarifa -> {
                    tarifaService.eliminarPorId(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}