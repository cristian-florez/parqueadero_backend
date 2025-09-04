package com.parqueadero.dtos.cierreTurno;

import java.time.LocalDateTime;
import java.util.List;

import com.parqueadero.dtos.vehiculos.TotalVehiculosDTO;
import com.parqueadero.models.Vehiculo;

public class TicketCierreTurno {

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCierre;
    private List<Vehiculo> listaVehiculosEntrantes;
    private List<Vehiculo> listaVehiculosSalientes;
    private Integer totalAPagar;
    private List<Vehiculo> vehiculosEnParqueadero;
    private List<TotalVehiculosDTO> listaTiposVehiculosEntrantes;
    private List<TotalVehiculosDTO> listaTiposVehiculosSalientes;
    private List<TotalVehiculosDTO> listaTiposVehiculosParqueadero;

    public TicketCierreTurno() {
    }

    public TicketCierreTurno(LocalDateTime fechaInicio, LocalDateTime fechaCierre,
                             List<Vehiculo> listaVehiculosEntrantes,
                             List<Vehiculo> listaVehiculosSalientes,
                             Integer totalAPagar,
                             List<Vehiculo> vehiculosEnParqueadero,
                             List<TotalVehiculosDTO> listaTiposVehiculosEntrantes,
                             List<TotalVehiculosDTO> listaTiposVehiculosSalientes,
                             List<TotalVehiculosDTO> listaTiposVehiculosParqueadero) {
        this.fechaInicio = fechaInicio;
        this.fechaCierre = fechaCierre;
        this.listaVehiculosEntrantes = listaVehiculosEntrantes;
        this.listaVehiculosSalientes = listaVehiculosSalientes;
        this.totalAPagar = totalAPagar;
        this.vehiculosEnParqueadero = vehiculosEnParqueadero;
        this.listaTiposVehiculosEntrantes = listaTiposVehiculosEntrantes;
        this.listaTiposVehiculosSalientes = listaTiposVehiculosSalientes;
        this.listaTiposVehiculosParqueadero = listaTiposVehiculosParqueadero;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

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
