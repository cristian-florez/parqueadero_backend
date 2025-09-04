package com.parqueadero.dtos.tickets;

import java.time.LocalDateTime;

public class TicketMensualidadRequest {

    private LocalDateTime fechaHoraEntrada;
    private long usuarioId;
    private String placa;
    private String tipoVehiculo;
    private int dias;
    private Integer total;

    public TicketMensualidadRequest() {
    }

    public TicketMensualidadRequest(LocalDateTime fechaHoraEntrada, long usuarioId, String placa, String tipoVehiculo, int dias, Integer total) {
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.usuarioId = usuarioId;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.dias = dias;
        this.total = total;
    }

    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
