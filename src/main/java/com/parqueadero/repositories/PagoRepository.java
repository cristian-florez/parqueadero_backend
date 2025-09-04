package com.parqueadero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parqueadero.models.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    @Query("SELECT p.estado FROM Pago p WHERE p.id = :id")
    Boolean findEstadoById(@Param("id") Long id);
}
