package com.parqueadero.services;

import com.parqueadero.models.Pago;
import com.parqueadero.models.Ticket;
import com.parqueadero.repositories.PagoRepository;

import com.parqueadero.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TarifaService tarifaService;

    @Autowired
    private TicketRepository ticketRepository;

    public List<Pago> buscarTodos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void eliminarPorId(Long id) {
        pagoRepository.deleteById(id);
    }


    public Integer calcularTotal(String codigo) {

        Ticket ticket = ticketRepository.findByCodigo(codigo);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket no encotrado");
        }

        Integer tarifa = tarifaService.buscarPorTipoVehiculo(ticket.getVehiculo().getTipo());
        if (tarifa == null) {
            throw new IllegalArgumentException("Tarifa no encontrada para el tipo de vehículo: " + ticket.getVehiculo().getTipo());
        }


        long minutos = Duration.between(ticket.getFechaHoraEntrada(), LocalDateTime.now()).toMinutes();
        long horas = (long) Math.ceil(minutos / 60.0);
        int bloques = (int) Math.ceil(horas / 24.0);

        Integer total = (bloques == 0) ? tarifa : bloques * tarifa;

        return total;
    }
}
