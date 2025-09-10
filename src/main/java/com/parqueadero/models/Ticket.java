package com.parqueadero.models;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigo;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;

    @ManyToOne
    @JoinColumn(name = "usuario_recibio_id")
    private Usuario usuarioRecibio;

    @ManyToOne
    @JoinColumn(name = "usuario_entrego_id")
    private Usuario usuarioEntrego;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "parqueadero_id", nullable = false)
    private Parqueadero parqueadero;

    public Ticket() {
    }

    public Ticket(String codigo, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida, Vehiculo vehiculo, Pago pago, Usuario usuarioRecibio, Usuario usuarioEntrego, Parqueadero parqueadero) {
        this.codigo = codigo;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.fechaHoraSalida = fechaHoraSalida;
        this.vehiculo = vehiculo;
        this.pago = pago;
        this.usuarioRecibio = usuarioRecibio;
        this.usuarioEntrego = usuarioEntrego;
        this.parqueadero = parqueadero;
    }

    public Ticket(Long id, String codigo, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida, Vehiculo vehiculo, Pago pago, Usuario usuarioRecibio, Usuario usuarioEntrego, Parqueadero parqueadero) {
        this.id = id;
        this.codigo = codigo;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.fechaHoraSalida = fechaHoraSalida;
        this.vehiculo = vehiculo;
        this.pago = pago;
        this.usuarioRecibio = usuarioRecibio;
        this.usuarioEntrego = usuarioEntrego;
        this.parqueadero = parqueadero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public Usuario getUsuarioRecibio() {
        return usuarioRecibio;
    }

    public void setUsuarioRecibio(Usuario usuarioRecibio) {
        this.usuarioRecibio = usuarioRecibio;
    }

    public Usuario getUsuarioEntrego() {
        return usuarioEntrego;
    }

    public void setUsuarioEntrego(Usuario usuarioEntrego) {
        this.usuarioEntrego = usuarioEntrego;
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

    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
    }
}
