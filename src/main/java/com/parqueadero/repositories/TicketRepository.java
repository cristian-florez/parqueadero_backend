package com.parqueadero.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parqueadero.dtos.vehiculos.TotalVehiculosDTO;
import com.parqueadero.models.Ticket;
import com.parqueadero.models.Vehiculo;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

       // Busca un ticket por su código de barras QR
       Ticket findByCodigo(String codigoBarras);

    /**
     * Obtiene TODOS los tickets financieramente relevantes para un turno.
     * Un ticket es relevante si:
     * 1. El vehículo ENTRÓ durante el turno.
     * 2. El vehículo SALIÓ durante el turno.
     * 3. El PAGO se realizó durante el turno (clave para mensualidades).
     * Se usa JOIN FETCH para optimizar y traer todas las entidades relacionadas.
     */
    @Query("SELECT DISTINCT t FROM Ticket t " +
            "LEFT JOIN FETCH t.vehiculo " +
            "LEFT JOIN FETCH t.parqueadero " +
            "LEFT JOIN FETCH t.pago p " +
            "WHERE (t.fechaHoraEntrada BETWEEN :fechaInicio AND :fechaFin) " +
            "OR (t.fechaHoraSalida BETWEEN :fechaInicio AND :fechaFin) " +
            "OR (p.fechaHora BETWEEN :fechaInicio AND :fechaFin)")
    List<Ticket> findTicketsParaCierre(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    /**
     * Obtiene todos los tickets de vehículos que están actualmente dentro del parqueadero.
     * Es necesaria para el "inventario" de vehículos restantes.
     */
    @Query("SELECT t FROM Ticket t " +
            "LEFT JOIN FETCH t.vehiculo " +
            "LEFT JOIN FETCH t.parqueadero " +
            "WHERE t.pago.estado = false")
    List<Ticket> findTicketsAbiertos();

}
