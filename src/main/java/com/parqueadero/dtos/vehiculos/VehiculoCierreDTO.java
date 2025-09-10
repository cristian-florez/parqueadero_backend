package com.parqueadero.dtos.vehiculos;

public class VehiculoCierreDTO {

    private String placa;
    private String tipo;
    private Integer totalCobrado;

    public VehiculoCierreDTO() {
    }

    public VehiculoCierreDTO(String placa, String tipo, Integer totalCobrado) {
        this.placa = placa;
        this.tipo = tipo;
        this.totalCobrado = totalCobrado;
    }

    // Getters y Setters

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getTotalCobrado() {
        return totalCobrado;
    }

    public void setTotalCobrado(Integer totalCobrado) {
        this.totalCobrado = totalCobrado;
    }
}
