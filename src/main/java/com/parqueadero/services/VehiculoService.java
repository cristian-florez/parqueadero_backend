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
        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setPlaca(placa);
        vehiculo.setTipo(tipo);
        
        return vehiculoRepository.save(vehiculo);
    }

    public Optional<Vehiculo> actualizarVehiculo(Long id, Vehiculo vehiculoDetails) {
        return vehiculoRepository.findById(id).map(vehiculo -> {
            vehiculo.setPlaca(vehiculoDetails.getPlaca());
            vehiculo.setTipo(vehiculoDetails.getTipo());
            return vehiculoRepository.save(vehiculo);
        });
    }

    public boolean findByPlaca(String placa) {
        return vehiculoRepository.existsByPlaca(placa);
    }
}
