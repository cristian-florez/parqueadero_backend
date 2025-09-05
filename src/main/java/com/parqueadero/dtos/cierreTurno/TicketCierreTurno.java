package com.parqueadero.dtos.cierreTurno;

import java.time.LocalDateTime;
import java.util.Map;

public class TicketCierreTurno {

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCierre;
    private Integer total;
    private Map<String, DetalleParqueaderoCierre> detallesPorParqueadero;

    public TicketCierreTurno() {
    }

    public TicketCierreTurno(LocalDateTime fechaInicio, LocalDateTime fechaCierre, Integer total, Map<String, DetalleParqueaderoCierre> detallesPorParqueadero) {
        this.fechaInicio = fechaInicio;
        this.fechaCierre = fechaCierre;
        this.total = total;
        this.detallesPorParqueadero = detallesPorParqueadero;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Map<String, DetalleParqueaderoCierre> getDetallesPorParqueadero() {
        return detallesPorParqueadero;
    }

    public void setDetallesPorParqueadero(Map<String, DetalleParqueaderoCierre> detallesPorParqueadero) {
        this.detallesPorParqueadero = detallesPorParqueadero;
    }
}
