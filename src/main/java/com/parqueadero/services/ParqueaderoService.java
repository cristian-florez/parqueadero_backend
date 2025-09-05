package com.parqueadero.services;

import com.parqueadero.models.Parqueadero;
import com.parqueadero.repositories.ParqueaderoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParqueaderoService {

    @Autowired
    private ParqueaderoRepository parqueaderoRepository;

    @Transactional
    public Parqueadero buscarOCrear(String nombre) {
        return parqueaderoRepository.findByNombre(nombre).orElseGet(() -> {
            Parqueadero nuevoParqueadero = new Parqueadero();
            nuevoParqueadero.setNombre(nombre);
            return parqueaderoRepository.save(nuevoParqueadero);
        });
    }

    public List<Parqueadero> buscarTodos() {
        return parqueaderoRepository.findAll();
    }
}

