package com.parqueadero.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parqueadero.models.Ticket;
import com.parqueadero.models.Vehiculo;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

    // Busca un ticket por su código de barras QR
    Ticket findByCodigoBarrasQR(String codigoBarras);

    // Busca un ticket por su código de barras QR y estado de pago
    Ticket findByCodigoBarrasQRAndPagado(String codigoBarras, boolean pagado);

    // Obtiene tickets cuya fecha de entrada o salida esté dentro del rango dado
    @Query("SELECT t FROM Ticket t " +
           "WHERE (t.fechaHoraEntrada BETWEEN :fechaInicio AND :fechaFin) " +
           "   OR (t.fechaHoraSalida BETWEEN :fechaInicio AND :fechaFin)")
    List<Ticket> findByFechaEntradaOrSalidaBetween(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin);

    // trae la cantidad de vehículos que entraron en un rango de fechas y aún no han salido,
    // agrupados por tipo de vehículo (carro, moto, etc.)
    @Query("SELECT v.tipo, COUNT(v.tipo) FROM Ticket t JOIN t.vehiculo v " +
           "WHERE t.fechaHoraEntrada BETWEEN :fechaInicio AND :fechaFin GROUP BY v.tipo")
    List<Object> findTotalVehiculosEntrantes(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin);

    // trae la cantidad de vehículos que salieron en un rango de fechas,
    // agrupados por tipo de vehículo
    @Query("SELECT v.tipo, COUNT(v.tipo) FROM Ticket t JOIN t.vehiculo v " +
           "WHERE t.fechaHoraEntrada IS NOT NULL " +
           "AND t.fechaHoraSalida IS NOT NULL " +
           "AND t.fechaHoraSalida BETWEEN :fechaInicio AND :fechaFin GROUP BY v.tipo")
    List<Object> findTotalVehiculosSalientes(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT v.tipo, COUNT(v.tipo) FROM Ticket t JOIN t.vehiculo v " +
           "WHERE t.pagado = false GROUP BY v.tipo")
    List<Object> findTotalVehiculosParqueadero();

    // Obtiene todos los tickets según si fueron pagados o no
    List<Ticket> findByPagado(boolean pagado);

    // Suma el total de pagos de tickets que ya fueron pagados y cuya fecha de salida
    // esté dentro del rango de fechas
    @Query("SELECT SUM(p.total) FROM Ticket t JOIN t.pago p " +
           "WHERE t.pagado = true AND t.fechaHoraSalida BETWEEN :fechaInicio AND :fechaFin")
    Double findTotal(@Param("fechaInicio") LocalDateTime fechaInicio,
                     @Param("fechaFin") LocalDateTime fechaFin);

    // Devuelve los vehículos que entraron en un rango de fechas y aún no tienen salida
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

    // Suma el total de pagos de los tickets que entraron durante un turno (rango de fechas)
       @Query("SELECT SUM(p.total) " +
              "FROM Ticket t " +
              "JOIN t.pago p " +
              "JOIN t.vehiculo v " +
              "WHERE " +
              " (t.fechaHoraSalida BETWEEN :fechaInicioTurno AND :fechaCierreTurno) " +
              " OR " +
              " (v.placa LIKE CONCAT('%', :mensualidad, '%') " +
              "  AND t.fechaHoraEntrada BETWEEN :fechaInicioTurno AND :fechaCierreTurno)")
       Double totalCierreTurno(
              @Param("fechaInicioTurno") LocalDateTime fechaInicioTurno,
              @Param("fechaCierreTurno") LocalDateTime fechaCierreTurno,
              @Param("mensualidad") String mensualidad);




    // Devuelve todos los vehículos que todavía no tienen registrada una fecha de salida
    @Query("SELECT t.vehiculo FROM Ticket t WHERE t.fechaHoraSalida IS NULL")
    List<Vehiculo> findVehiculosSinSalida();

}
