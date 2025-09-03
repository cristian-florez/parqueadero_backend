package com.parqueadero.services;

import com.parqueadero.models.Tarifa;
import com.parqueadero.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    public List<Tarifa> buscarTodas() {
        return tarifaRepository.findAll();
    }

    public Optional<Tarifa> buscarPorId(Long id) {
        return tarifaRepository.findById(id);
    }

    public Tarifa guardar(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }

    public void eliminarPorId(Long id) {
        tarifaRepository.deleteById(id);
    }

    public Integer buscarPorTipoVehiculo(String tipoVehiculo) {
        return tarifaRepository.findPrecioByTipoVehiculo(tipoVehiculo);
    }

    public Optional<Tarifa> actualizarTarifa(Long id, Tarifa tarifaDetails) {
        return tarifaRepository.findById(id).map(tarifa -> {
            tarifa.setTipoVehiculo(tarifaDetails.getTipoVehiculo());
            tarifa.setPrecioDia(tarifaDetails.getPrecioDia());
            return tarifaRepository.save(tarifa);
        });
    }
}
