package com.parqueadero.mappers;

import com.parqueadero.dtos.tickets.TicketResponse;
import com.parqueadero.models.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    // Entidad → Response
    @Mapping(source = "vehiculo.placa", target = "placa")
    @Mapping(source = "vehiculo.tipo", target = "tipoVehiculo")
    @Mapping(source = "usuarioRecibio.nombre", target = "usuarioRecibio")
    @Mapping(source = "usuarioEntrego.nombre", target = "usuarioEntrego")
    @Mapping(source = "pago.estado", target = "estadoPago")
    @Mapping(source = "pago.total", target = "totalPagar")
    TicketResponse toResponse(Ticket ticket);
}
