package com.parqueadero.dtos.tickets;

public class TicketEntradaRequest {

    private String placa;
    private String tipoVehiculo;
    private long usuarioRecibioId;
    private String parqueadero;

    public TicketEntradaRequest() {
    }

    public TicketEntradaRequest(String placa, String tipoVehiculo, long usuarioRecibioId, String parqueadero) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.usuarioRecibioId = usuarioRecibioId;
        this.parqueadero = parqueadero;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public long getUsuarioRecibioId() {
        return usuarioRecibioId;
    }

    public void setUsuarioRecibioId(long usuarioRecibioId) {
        this.usuarioRecibioId = usuarioRecibioId;
    }

    public String getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(String parqueadero) {
        this.parqueadero = parqueadero;
    }
}
