package com.parqueadero.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tarifas")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tipoVehiculo;
    private Integer precioDia;
    
    public Tarifa() {
    }

    public Tarifa(String tipoVehiculo, Integer precioDia) {
        this.tipoVehiculo = tipoVehiculo;
        this.precioDia = precioDia;
    }

    public Tarifa(long id, String tipoVehiculo, Integer precioDia) {
        this.id = id;
        this.tipoVehiculo = tipoVehiculo;
        this.precioDia = precioDia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Integer getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(Integer precioDia) {
        this.precioDia = precioDia;
    }

}
