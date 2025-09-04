package com.parqueadero.dtos.cierreTurno;

import java.time.LocalDateTime;

public class CierreReimpresionResponse {

    private String nombreUsuario;
    private LocalDateTime fechaInicioTurno;
    private LocalDateTime fechaFinTurno;
    private Integer totalIngresos;
    private String detalleEntrantes;
    private String detalleSalientes;
    private String detalleRestantes;

    public CierreReimpresionResponse() {
    }

    public CierreReimpresionResponse(String nombreUsuario, LocalDateTime fechaInicioTurno,
                                     LocalDateTime fechaFinTurno, Integer totalIngresos,
                                     String detalleEntrantes, String detalleSalientes,
                                     String detalleRestantes) {
        this.nombreUsuario = nombreUsuario;
        this.fechaInicioTurno = fechaInicioTurno;
        this.fechaFinTurno = fechaFinTurno;
        this.totalIngresos = totalIngresos;
        this.detalleEntrantes = detalleEntrantes;
        this.detalleSalientes = detalleSalientes;
        this.detalleRestantes = detalleRestantes;
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
}
