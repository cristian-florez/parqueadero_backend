package com.parqueadero.models.DTOS;

import java.time.LocalDateTime;

// Dejamos el DTO como un record plano y simple.
public record TicketEntrada(
    String placa,
    String tipoVehiculo,
    String usuarioRecibio,
    LocalDateTime fechaHoraEntrada,
    Integer dias,
    Boolean pagado,
    Integer total
) {}