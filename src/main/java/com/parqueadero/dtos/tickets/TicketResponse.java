package com.parqueadero.dtos.tickets;

import java.time.LocalDateTime;

public class TicketResponse {

    private String codigo;
    private String placa;
    private String tipoVehiculo;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private Boolean estadoPago;
    private Integer totalPagar;
    private String usuarioRecibio;
    private String usuarioEntrego;

    public TicketResponse() {
    }

    public TicketResponse(String codigo, String placa, String tipoVehiculo, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida, Boolean estadoPago, Integer totalPagar, String usuarioRecibio, String usuarioEntrego) {
        this.codigo = codigo;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.fechaHoraSalida = fechaHoraSalida;
        this.estadoPago = estadoPago;
        this.totalPagar = totalPagar;
        this.usuarioRecibio = usuarioRecibio;
        this.usuarioEntrego = usuarioEntrego;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public Boolean getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(Boolean estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Integer getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Integer totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getUsuarioRecibio() {
        return usuarioRecibio;
    }

    public void setUsuarioRecibio(String usuarioRecibio) {
        this.usuarioRecibio = usuarioRecibio;
    }

    public String getUsuarioEntrego() {
        return usuarioEntrego;
    }

    public void setUsuarioEntrego(String usuarioEntrego) {
        this.usuarioEntrego = usuarioEntrego;
    }
}
