package com.parqueadero.models.DTOS;

import java.time.LocalDateTime;

public record TicketEntrada(
    String codigoBarras,
    String placa,
    String tipoVehiculo,
    LocalDateTime fechaHoraEntrada,
    String usuarioRecibio) {}

