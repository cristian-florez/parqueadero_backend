package com.parqueadero.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.parqueadero.dtos.cierreTurno.DetalleParqueaderoCierre;
import com.parqueadero.dtos.cierreTurno.TicketCierreTurnoResponse;
import com.parqueadero.models.CierreTurno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface CierreTurnoMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombreUsuario", target = "nombreUsuario")
    @Mapping(source = "fechaInicioTurno", target = "fechaInicio")
    @Mapping(source = "fechaFinTurno", target = "fechaCierre")
    @Mapping(source = "totalIngresos", target = "total")
    @Mapping(target = "detallesPorParqueadero", expression = "java(parseDetalles(cierreTurno.getDetallesJson()))")
    TicketCierreTurnoResponse toResponse(CierreTurno cierreTurno);

    // Método auxiliar para convertir el JSON de detalles a Map
    default Map<String, DetalleParqueaderoCierre> parseDetalles(String detallesJson) {
        if (detallesJson == null || detallesJson.isEmpty()) {
            return Map.of();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(detallesJson,
                    new TypeReference<Map<String, DetalleParqueaderoCierre>>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear detallesJson", e);
        }
    }
}
