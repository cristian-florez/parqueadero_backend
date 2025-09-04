package com.parqueadero.mappers;

import com.parqueadero.dtos.cierreTurno.CierreReimpresionResponse;
import com.parqueadero.dtos.cierreTurno.TicketCierreResponse;
import com.parqueadero.models.CierreTurno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CierreTurnoMapper {

    // Modelo -> TicketResponse (para mostrar en tabla)
    @Mapping(source = "nombreUsuario", target = "usuario")
    @Mapping(source = "fechaInicioTurno", target = "fechaInicio")
    @Mapping(source = "fechaFinTurno", target = "fechaFinal")
    @Mapping(source = "totalIngresos", target = "total")
    TicketCierreResponse toResponse(CierreTurno cierreTurno);

    // Modelo -> CierreTurnoReimpresionResponse (para reimpresión)
    CierreReimpresionResponse toReimpresionResponse(CierreTurno cierreTurno);
}
