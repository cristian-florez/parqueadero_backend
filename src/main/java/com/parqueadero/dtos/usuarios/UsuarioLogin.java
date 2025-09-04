package com.parqueadero.dtos.usuarios;

public class UsuarioLogin {

    private String nombre;
    private String cedula;

    public UsuarioLogin() {
    }

    public UsuarioLogin(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
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
}
