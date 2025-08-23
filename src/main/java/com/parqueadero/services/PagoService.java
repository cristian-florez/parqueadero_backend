package com.parqueadero.services;

import com.parqueadero.models.Pago;
import com.parqueadero.models.Tarifa;
import com.parqueadero.repositories.PagoRepository;
import com.parqueadero.repositories.TarifaRepository;
import com.parqueadero.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public List<Pago> buscarTodos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public Pago calcularTotal(String codigo, String vehiculo, LocalDateTime horaEntrada, LocalDateTime horaSalida) {
        Tarifa tarifa = tarifaRepository.findByTipoVehiculo(vehiculo);
        if (tarifa != null) {
            Pago pago = new Pago();
            Integer total = 0;
            Integer precioVehiculo = tarifa.getPrecioDia();
            long horas = java.time.Duration.between(horaEntrada, horaSalida).toHours();

            int calcularTotal = (int)(horas / 12);
            if (calcularTotal == 0) {
                total = precioVehiculo;
            } else {
                total = calcularTotal * precioVehiculo;
            }
            pago.setTicket(ticketRepository.findByCodigoBarrasQR(codigo));
            pago.setTotal(total);
            pago.setFechaHora(java.time.LocalDateTime.now());
            return pago;


        } else {
            throw new IllegalArgumentException("Tarifa no encontrada para el tipo de vehículo: " + vehiculo);
        }
    }


    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void eliminarPorId(Long id) {
        pagoRepository.deleteById(id);
    }
}