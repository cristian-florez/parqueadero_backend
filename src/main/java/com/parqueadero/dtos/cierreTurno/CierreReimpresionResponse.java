package com.parqueadero.dtos.cierreTurno;

import java.time.LocalDateTime;

public class CierreReimpresionResponse {

    private String nombreUsuario;
    private LocalDateTime fechaInicioTurno;
    private LocalDateTime fechaFinTurno;
    private Integer totalIngresos;
    private String detallesJson;


    public CierreReimpresionResponse() {
    }

    public CierreReimpresionResponse(String nombreUsuario, LocalDateTime fechaInicioTurno,
                                     LocalDateTime fechaFinTurno, Integer totalIngresos,
                                     String detallesJson) {
        this.nombreUsuario = nombreUsuario;
        this.fechaInicioTurno = fechaInicioTurno;
        this.fechaFinTurno = fechaFinTurno;
        this.totalIngresos = totalIngresos;
        this.detallesJson = detallesJson;

    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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

    public String getDetallesJson() {
        return detallesJson;
    }

    public void setDetallesJson(String detallesJson) {
        this.detallesJson = detallesJson;
    }

}
