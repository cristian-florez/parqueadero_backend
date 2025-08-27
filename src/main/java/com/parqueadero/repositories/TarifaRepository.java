package com.parqueadero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parqueadero.models.Tarifa;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    Tarifa findByTipoVehiculo(String tipoVehiculo);

    @Query("SELECT t.precioDia FROM Tarifa t WHERE t.tipoVehiculo = :tipoVehiculo")
    Integer findPrecioByTipoVehiculo(String tipoVehiculo);
}
