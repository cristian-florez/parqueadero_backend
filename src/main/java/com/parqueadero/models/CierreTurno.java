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

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaInicioTurno;
    private LocalDateTime fechaFinTurno;

    private int totalVehiculosEntraron;
    private int totalVehiculosSalieron;
    private double totalIngresos;
    private int vehiculosRestantes;

    private String detalleEntrantes;
    private String detalleSalientes;
    private String detalleRestantes;
    private String nombreUsuario;

    public CierreTurno() {
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public int getTotalVehiculosEntraron() {
        return totalVehiculosEntraron;
    }

    public void setTotalVehiculosEntraron(int totalVehiculosEntraron) {
        this.totalVehiculosEntraron = totalVehiculosEntraron;
    }

    public int getTotalVehiculosSalieron() {
        return totalVehiculosSalieron;
    }

    public void setTotalVehiculosSalieron(int totalVehiculosSalieron) {
        this.totalVehiculosSalieron = totalVehiculosSalieron;
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public int getVehiculosRestantes() {
        return vehiculosRestantes;
    }

    public void setVehiculosRestantes(int vehiculosRestantes) {
        this.vehiculosRestantes = vehiculosRestantes;
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
