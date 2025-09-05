package com.parqueadero.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CierreTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaInicioTurno;
    private LocalDateTime fechaFinTurno;
    private Integer totalIngresos; // Este campo guardará la suma de todos los parqueaderos
    private String nombreUsuario;

    // CAMBIO PRINCIPAL: Un único campo para guardar todos los detalles.
    @Lob // Anotación para Large Object, ideal para texto largo.
    @Column(columnDefinition = "TEXT")
    private String detallesJson;

    public CierreTurno() {
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaInicioTurno() {
        return fechaInicioTurno;
    }

    public void setFechaInicioTurno(LocalDateTime fechaInicioTurno) {
        this.fechaInicioTurno = fechaInicioTurno;
    }

    public LocalDateTime getFechaFinTurno() {
        return fechaFinTurno;
    }

    public void setFechaFinTurno(LocalDateTime fechaFinTurno) {
        this.fechaFinTurno = fechaFinTurno;
    }

    public Integer getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(Integer totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDetallesJson() {
        return detallesJson;
    }

    public void setDetallesJson(String detallesJson) {
        this.detallesJson = detallesJson;
    }
}
