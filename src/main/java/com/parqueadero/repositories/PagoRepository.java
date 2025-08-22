package com.parqueadero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parqueadero.models.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}
