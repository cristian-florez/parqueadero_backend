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

    public Vehiculo crearVehiculo(String placa, String tipo) {
        return vehiculoRepository.findByPlaca(placa).map(vehiculoExistente -> {
            // Si el vehículo existe y el tipo es diferente, lo actualiza.
            if (!vehiculoExistente.getTipo().equalsIgnoreCase(tipo)) {
                vehiculoExistente.setTipo(tipo);
                return vehiculoRepository.save(vehiculoExistente);
            }
            // Si el tipo es el mismo, simplemente lo devuelve.
            return vehiculoExistente;
        }).orElseGet(() -> {
            // Si no existe, crea uno nuevo.
            Vehiculo vehiculoNuevo = new Vehiculo();
            vehiculoNuevo.setPlaca(placa);
            vehiculoNuevo.setTipo(tipo);
            return vehiculoRepository.save(vehiculoNuevo);
        });
    }

}
