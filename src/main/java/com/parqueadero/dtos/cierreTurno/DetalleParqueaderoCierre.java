package com.parqueadero.dtos.cierreTurno;

import com.parqueadero.dtos.vehiculos.TotalVehiculosDTO;
import com.parqueadero.models.Vehiculo;

import java.util.List;

public class DetalleParqueaderoCierre {

    private List<Vehiculo> listaVehiculosEntrantes;
    private List<Vehiculo> listaVehiculosSalientes;
    private Integer totalAPagar;
    private List<Vehiculo> vehiculosMensualidad;
    private List<Vehiculo> vehiculosEnParqueadero;
    private List<TotalVehiculosDTO> listaTiposVehiculosEntrantes;
    private List<TotalVehiculosDTO> listaTiposVehiculosSalientes;
    private List<TotalVehiculosDTO> listaTiposVehiculosParqueadero;

    // Getters y Setters

    public List<Vehiculo> getListaVehiculosEntrantes() {
        return listaVehiculosEntrantes;
    }

    public void setListaVehiculosEntrantes(List<Vehiculo> listaVehiculosEntrantes) {
        this.listaVehiculosEntrantes = listaVehiculosEntrantes;
    }

    public List<Vehiculo> getListaVehiculosSalientes() {
        return listaVehiculosSalientes;
    }

    public void setListaVehiculosSalientes(List<Vehiculo> listaVehiculosSalientes) {
        this.listaVehiculosSalientes = listaVehiculosSalientes;
    }

    public Integer getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(Integer totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public List<Vehiculo> getVehiculosEnParqueadero() {
        return vehiculosEnParqueadero;
    }

    public void setVehiculosEnParqueadero(List<Vehiculo> vehiculosEnParqueadero) {
        this.vehiculosEnParqueadero = vehiculosEnParqueadero;
    }

    public List<Vehiculo> getVehiculosMensualidad() {
        return vehiculosMensualidad;
    }

    public void setVehiculosMensualidad(List<Vehiculo> vehiculosMensualidad) {
        this.vehiculosMensualidad = vehiculosMensualidad;
    }

    public List<TotalVehiculosDTO> getListaTiposVehiculosEntrantes() {
        return listaTiposVehiculosEntrantes;
    }

    public void setListaTiposVehiculosEntrantes(List<TotalVehiculosDTO> listaTiposVehiculosEntrantes) {
        this.listaTiposVehiculosEntrantes = listaTiposVehiculosEntrantes;
    }

    public List<TotalVehiculosDTO> getListaTiposVehiculosSalientes() {
        return listaTiposVehiculosSalientes;
    }

    public void setListaTiposVehiculosSalientes(List<TotalVehiculosDTO> listaTiposVehiculosSalientes) {
        this.listaTiposVehiculosSalientes = listaTiposVehiculosSalientes;
    }

    public List<TotalVehiculosDTO> getListaTiposVehiculosParqueadero() {
        return listaTiposVehiculosParqueadero;
    }

    public void setListaTiposVehiculosParqueadero(List<TotalVehiculosDTO> listaTiposVehiculosParqueadero) {
        this.listaTiposVehiculosParqueadero = listaTiposVehiculosParqueadero;
    }
}
