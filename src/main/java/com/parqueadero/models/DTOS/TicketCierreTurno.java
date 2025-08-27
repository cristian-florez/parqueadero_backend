package com.parqueadero.models.DTOS;

import java.util.List;

import com.parqueadero.models.Vehiculo;

public class TicketCierreTurno {

    private List<Vehiculo> totalVehiculosQueEntraron;
    private List<Vehiculo> totalVehiculosQueSalieron;
    private double totalAPagar;
    private List<Vehiculo> vehiculosEnParqueadero;
    private List<Object> tipoVehiculosEntrantes;
    private List<Object> tipoVehiculosSaliente;
    private List<Object> tipoVehiculosParqueadero;

    public TicketCierreTurno() {
    }

    public TicketCierreTurno(List<Vehiculo> totalVehiculosQueEntraron, List<Vehiculo> totalVehiculosQueSalieron, double totalAPagar, List<Vehiculo> vehiculosEnParqueadero, List<Object> tipoVehiculosEntrantes, List<Object> tipoVehiculosSaliente, List<Object> tipoVehiculosParqueadero) {
        this.totalVehiculosQueEntraron = totalVehiculosQueEntraron;
        this.totalVehiculosQueSalieron = totalVehiculosQueSalieron;
        this.totalAPagar = totalAPagar;
        this.vehiculosEnParqueadero = vehiculosEnParqueadero;
        this.tipoVehiculosEntrantes = tipoVehiculosEntrantes;
        this.tipoVehiculosSaliente = tipoVehiculosSaliente;
    }

    public List<Vehiculo> getTotalVehiculosQueEntraron() {
        return totalVehiculosQueEntraron;
    }

    public void setTotalVehiculosQueEntraron(List<Vehiculo> totalVehiculosQueEntraron) {
        this.totalVehiculosQueEntraron = totalVehiculosQueEntraron;
    }

    public List<Vehiculo> getTotalVehiculosQueSalieron() {
        return totalVehiculosQueSalieron;
    }

    public void setTotalVehiculosQueSalieron(List<Vehiculo> totalVehiculosQueSalieron) {
        this.totalVehiculosQueSalieron = totalVehiculosQueSalieron;
    }

    public double getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(double totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public List<Vehiculo> getVehiculosEnParqueadero() {
        return vehiculosEnParqueadero;
    }

    public void setVehiculosEnParqueadero(List<Vehiculo> vehiculosEnParqueadero) {
        this.vehiculosEnParqueadero = vehiculosEnParqueadero;
    }


    public List<Object> getTipoVehiculosEntrantes() {
        return tipoVehiculosEntrantes;
    }

    public void setTipoVehiculosEntrantes(List<Object> tipoVehiculosEntrantes) {
        this.tipoVehiculosEntrantes = tipoVehiculosEntrantes;
    }

    public List<Object> getTipoVehiculosSaliente() {
        return tipoVehiculosSaliente;
    }

    public void setTipoVehiculosSaliente(List<Object> tipoVehiculosSaliente) {
        this.tipoVehiculosSaliente = tipoVehiculosSaliente;
    }

    public List<Object> getTipoVehiculosParqueadero() {
        return tipoVehiculosParqueadero;
    }

    public void setTipoVehiculosParqueadero(List<Object> tipoVehiculosParqueadero) {
        this.tipoVehiculosParqueadero = tipoVehiculosParqueadero;
    }

}
