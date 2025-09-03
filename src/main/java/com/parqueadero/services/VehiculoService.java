package com.parqueadero.services;

import com.parqueadero.models.Vehiculo;
import com.parqueadero.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> buscarTodos() {
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> buscarPorId(Long id) {
        return vehiculoRepository.findById(id);
    }

    public Vehiculo guardar(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public void eliminarPorId(Long id) {
        vehiculoRepository.deleteById(id);
    }

    public Optional<Vehiculo> actualizarVehiculo(Long id, Vehiculo vehiculoDetails) {
        return vehiculoRepository.findById(id).map(vehiculo -> {
            vehiculo.setPlaca(vehiculoDetails.getPlaca());
            vehiculo.setTipo(vehiculoDetails.getTipo());
            return vehiculoRepository.save(vehiculo);
        });
    }

    public Vehiculo findByPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa);
    }

    public Vehiculo findOrCreateVehiculo(String placa, String tipo) {
        Vehiculo vehiculo = findByPlaca(placa);
        if (vehiculo == null) {
            vehiculo = new Vehiculo(placa, tipo);
            guardar(vehiculo);
        }
        return vehiculo;
    }
}
