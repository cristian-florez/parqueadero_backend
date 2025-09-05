package com.parqueadero.repositories;

import com.parqueadero.models.Parqueadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Long> {
    Optional<Parqueadero> findByNombre(String nombre);
}
