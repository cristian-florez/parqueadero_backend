package com.parqueadero.dtos.cierreTurno;

import java.time.LocalDateTime;

public class TicketCierreResponse {

    private String usuario;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private Integer total;

    public TicketCierreResponse() {
    }

    public TicketCierreResponse(String usuario, LocalDateTime fechaInicio, LocalDateTime fechaFinal, Integer total) {
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.total = total;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDateTime fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
