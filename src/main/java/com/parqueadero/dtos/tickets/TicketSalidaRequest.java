package com.parqueadero.dtos.tickets;

public class TicketSalidaRequest {

    private String codigo;
    private long idUsuarioLogueado;

    public TicketSalidaRequest() {
    }

    public TicketSalidaRequest(String codigo, long idUsuarioLogueado) {
        this.codigo = codigo;
        this.idUsuarioLogueado = idUsuarioLogueado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public long getIdUsuarioLogueado() {
        return idUsuarioLogueado;
    }

    public void setIdUsuarioLogueado(long idUsuarioLogueado) {
        this.idUsuarioLogueado = idUsuarioLogueado;
    }
}
