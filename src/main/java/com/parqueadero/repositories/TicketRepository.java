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

       // trae la cantidad de vehículos que entraron en un rango de fechas,
       // agrupados por tipo de vehículo (carro, moto, etc.)
       @Query("SELECT new com.parqueadero.dtos.vehiculos.TotalVehiculosDTO(v.tipo, COUNT(v.tipo)) " +
                     "FROM Ticket t JOIN t.vehiculo v " +
                     "WHERE t.fechaHoraEntrada BETWEEN :fechaInicio AND :fechaFin " +
                     "GROUP BY v.tipo")
       List<TotalVehiculosDTO> findTotalVehiculosEntrantes(
                     @Param("fechaInicio") LocalDateTime fechaInicio,
                     @Param("fechaFin") LocalDateTime fechaFin);

       // trae la cantidad de vehículos que salieron en un rango de fechas,
       // agrupados por tipo de vehículo
       @Query("SELECT new com.parqueadero.dtos.vehiculos.TotalVehiculosDTO(v.tipo, COUNT(v.tipo)) " +
                     "FROM Ticket t " +
                     "JOIN t.vehiculo v " +
                     "WHERE t.fechaHoraEntrada IS NOT NULL " +
                     "AND t.fechaHoraSalida IS NOT NULL " +
                     "AND t.fechaHoraSalida BETWEEN :fechaInicio AND :fechaFin " +
                     "GROUP BY v.tipo")
       List<TotalVehiculosDTO> findTotalVehiculosSalientes(
                     @Param("fechaInicio") LocalDateTime fechaInicio,
                     @Param("fechaFin") LocalDateTime fechaFin);

       // obtenemos el total de vehiculos en el parqueadero
       // agrupado por tipo de vehiculo
       @Query("SELECT new com.parqueadero.dtos.vehiculos.TotalVehiculosDTO(v.tipo, COUNT(v.tipo)) " +
                     "FROM Ticket t " +
                     "JOIN t.vehiculo v " +
                     "JOIN t.pago p " +
                     "WHERE p.estado = false " +
                     "GROUP BY v.tipo")
       List<TotalVehiculosDTO> findTotalVehiculosParqueadero();

       //trae la lista de los vehiculos en parqueadero
       @Query("SELECT v " +
                     "FROM Ticket t " +
                     "JOIN t.vehiculo v " +
                     "JOIN t.pago p " +
                     "WHERE p.estado = false")
       List<Vehiculo> findVehiculosEnParqueadero();

       // Devuelve los vehículos que entraron en un rango de fechas y aún no tienen
       // salida
       @Query("SELECT t.vehiculo FROM Ticket t " +
                     "WHERE t.fechaHoraEntrada BETWEEN :fechaInicio AND :fechaFin ")
       List<Vehiculo> vehiculosQueEntraron(
                     @Param("fechaInicio") LocalDateTime fechaInicio,
                     @Param("fechaFin") LocalDateTime fechaFin);

       // Devuelve los vehículos que salieron dentro del turno (rango de fechas)
       @Query("SELECT t.vehiculo FROM Ticket t " +
                     "WHERE t.fechaHoraSalida BETWEEN :fechaInicioTurno AND :fechaCierreTurno")
       List<Vehiculo> vehiculosQueSalieron(
                     @Param("fechaInicioTurno") LocalDateTime fechaInicioTurno,
                     @Param("fechaCierreTurno") LocalDateTime fechaCierreTurno);

       // Suma el total de pagos de los tickets que entraron durante un turno (rango de
       // fechas)
       @Query("SELECT SUM(p.total) " +
                     "FROM Ticket t " +
                     "JOIN t.pago p " +
                     "JOIN t.vehiculo v " +
                     "WHERE " +
                     " (p.fechaHora BETWEEN :fechaInicioTurno AND :fechaCierreTurno) " +
                     " OR " +
                     " (v.placa LIKE CONCAT('%', :mensualidad, '%') " +
                     "  AND p.fechaHora BETWEEN :fechaInicioTurno AND :fechaCierreTurno)")
       Integer totalCierreTurno(
                     @Param("fechaInicioTurno") LocalDateTime fechaInicioTurno,
                     @Param("fechaCierreTurno") LocalDateTime fechaCierreTurno,
                     @Param("mensualidad") String mensualidad);

}
