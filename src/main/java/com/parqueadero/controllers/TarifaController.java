package com.parqueadero.controllers;

import com.parqueadero.models.Tarifa;
import com.parqueadero.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @GetMapping
    public ResponseEntity<?> getAllTarifas() {
        try {
            List<Tarifa> tarifas = tarifaService.buscarTodas();
            if (tarifas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay tarifas disponibles");
            }
            return ResponseEntity.ok(tarifas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener tarifas: " + e.getMessage());
        }
    }
}
