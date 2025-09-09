package com.parqueadero.dtos.cierreTurno;

import java.time.LocalDateTime;
import java.util.Map;

public class TicketCierreTurnoResponse {

    private long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCierre;
    private Integer total;
    private String nombreUsuario;
    private Map<String, DetalleParqueaderoCierre> detallesPorParqueadero;

    public TicketCierreTurnoResponse() {
    }

    public TicketCierreTurnoResponse(long id, LocalDateTime fechaInicio, LocalDateTime fechaCierre, Integer total, String nombreUsuario, Map<String, DetalleParqueaderoCierre> detallesPorParqueadero) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaCierre = fechaCierre;
        this.total = total;
        this.nombreUsuario = nombreUsuario;
        this.detallesPorParqueadero = detallesPorParqueadero;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Map<String, DetalleParqueaderoCierre> getDetallesPorParqueadero() {
        return detallesPorParqueadero;
    }

    public void setDetallesPorParqueadero(Map<String, DetalleParqueaderoCierre> detallesPorParqueadero) {
        this.detallesPorParqueadero = detallesPorParqueadero;
    }
}
