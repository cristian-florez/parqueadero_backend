package com.parqueadero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parqueadero.models.Tarifa;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
}
