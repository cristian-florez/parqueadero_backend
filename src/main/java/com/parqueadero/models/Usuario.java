package com.parqueadero.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String cedula;
    private LocalDateTime fechaInicioSesion;

    public Usuario() {
    }

    public Usuario(String nombre, String cedula, String celular, String rol, String contrasena, String usuario, LocalDateTime fechaInicioSesion) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.fechaInicioSesion = fechaInicioSesion;
    }

    public Usuario(Long id, String nombre, String cedula, String celular, String rol, String contrasena, String usuario, LocalDateTime fechaInicioSesion) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.fechaInicioSesion = fechaInicioSesion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public LocalDateTime getFechaInicioSesion() {
        return fechaInicioSesion;
    }

    public void setFechaInicioSesion(LocalDateTime fechaInicioSesion) {
        this.fechaInicioSesion = fechaInicioSesion;
    }

}