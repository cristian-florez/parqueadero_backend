package com.parqueadero.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CierreTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaInicioTurno;
    private LocalDateTime fechaFinTurno;
    private Integer totalIngresos;
    private String detalleEntrantes;
    private String detalleSalientes;
    private String detalleRestantes;
    private String nombreUsuario;

    public CierreTurno() {
    }

    public CierreTurno(LocalDateTime fechaInicioTurno, LocalDateTime fechaFinTurno, Integer totalIngresos, String detalleEntrantes, String detalleSalientes, String detalleRestantes, String nombreUsuario) {
        this.fechaInicioTurno = fechaInicioTurno;
        this.fechaFinTurno = fechaFinTurno;
        this.totalIngresos = totalIngresos;
        this.detalleEntrantes = detalleEntrantes;
        this.detalleSalientes = detalleSalientes;
        this.detalleRestantes = detalleRestantes;
        this.nombreUsuario = nombreUsuario;
    }

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

    public String getDetalleEntrantes() {
        return detalleEntrantes;
    }

    public void setDetalleEntrantes(String detalleEntrantes) {
        this.detalleEntrantes = detalleEntrantes;
    }

    public String getDetalleSalientes() {
        return detalleSalientes;
    }

    public void setDetalleSalientes(String detalleSalientes) {
        this.detalleSalientes = detalleSalientes;
    }

    public String getDetalleRestantes() {
        return detalleRestantes;
    }

    public void setDetalleRestantes(String detalleRestantes) {
        this.detalleRestantes = detalleRestantes;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
