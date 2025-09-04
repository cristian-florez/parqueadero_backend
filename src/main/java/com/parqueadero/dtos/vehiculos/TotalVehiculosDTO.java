package com.parqueadero.dtos.vehiculos;

public class TotalVehiculosDTO {
    private String tipo;
    private Long cantidad;

    public TotalVehiculosDTO() {
    }

    public TotalVehiculosDTO(String tipo, Long cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
