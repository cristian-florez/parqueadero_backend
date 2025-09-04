package com.parqueadero.services;

import com.parqueadero.models.Pago;
import com.parqueadero.repositories.PagoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TarifaService tarifaService;

    public List<Pago> buscarTodos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void eliminarPorId(Long id) {
        pagoRepository.deleteById(id);
    }

    public Boolean obtenerEstadoPago(Long id) {
        return pagoRepository.findEstadoById(id);
    }


    public Integer calcularTotal(String codigo, String tipoVehiculo, LocalDateTime horaEntrada,
        LocalDateTime horaSalida) {
        Integer tarifa = tarifaService.buscarPorTipoVehiculo(tipoVehiculo);
        if (tarifa == null) {
            throw new IllegalArgumentException("Tarifa no encontrada para el tipo de vehículo: " + tipoVehiculo);
        }


        long minutos = Duration.between(horaEntrada, horaSalida).toMinutes();
        long horas = (long) Math.ceil(minutos / 60.0);
        int bloques = (int) Math.ceil(horas / 12.0);

        Integer total = (bloques == 0) ? tarifa : bloques * tarifa;

        return total;
    }
}
