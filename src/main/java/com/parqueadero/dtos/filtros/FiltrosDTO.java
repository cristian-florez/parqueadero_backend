package com.parqueadero.dtos.filtros;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltrosDTO {
    private List<String> usuarios;
    private List<String> tiposVehiculo;
    private List<String> parqueaderos;
}
