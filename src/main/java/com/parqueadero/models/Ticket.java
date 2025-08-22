package com.parqueadero.models;

import java.time.LocalDateTime;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoBarrasQR;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private Boolean pagado;
    private String usuarioRecibio;
    private String usuarioEntrego;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Pago pago;

    public Ticket() {
    }

    public Ticket(String codigoBarrasQR, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida, Boolean pagado,Vehiculo vehiculo, Pago pago, String usuarioRecibio, String usuarioEntrego) {
        this.codigoBarrasQR = codigoBarrasQR;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.fechaHoraSalida = fechaHoraSalida;
        this.pagado = pagado;
        this.vehiculo = vehiculo;
        this.pago = pago;
        this.usuarioRecibio = usuarioRecibio;
        this.usuarioEntrego = usuarioEntrego;
    }

    public Ticket(Long id, String codigoBarrasQR, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida, Boolean pagado, Vehiculo vehiculo, Pago pago, String usuarioRecibio, String usuarioEntrego) {
        this.id = id;
        this.codigoBarrasQR = codigoBarrasQR;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.fechaHoraSalida = fechaHoraSalida;
        this.pagado = pagado;
        this.vehiculo = vehiculo;
        this.pago = pago;
        this.usuarioRecibio = usuarioRecibio;
        this.usuarioEntrego = usuarioEntrego;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoBarrasQR() {
        return codigoBarrasQR;
    }

    public void setCodigoBarrasQR(String codigoBarrasQR) {
        this.codigoBarrasQR = codigoBarrasQR;
    }

    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public String getUsuarioRecibio() {
        return usuarioRecibio;
    }

    public void setUsuarioRecibio(String usuarioRecibio) {
        this.usuarioRecibio = usuarioRecibio;
    }

    public String getUsuarioEntrego() {
        return usuarioEntrego;
    }

    public void setUsuarioEntrego(String usuarioEntrego) {
        this.usuarioEntrego = usuarioEntrego;
    }

    public Boolean getPagado() {
        return pagado;
    }

    public void setPagado(Boolean pagado) {
        this.pagado = pagado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }  
}
