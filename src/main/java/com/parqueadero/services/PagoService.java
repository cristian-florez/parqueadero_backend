package com.parqueadero.services;

import com.parqueadero.models.Pago;
import com.parqueadero.models.Tarifa;
import com.parqueadero.models.Ticket;
import com.parqueadero.repositories.PagoRepository;
import com.parqueadero.repositories.TarifaRepository;
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
    private TarifaRepository tarifaRepository;

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

    public Optional<Pago> actualizarPago(Long id, Pago pagoDetails) {
        return pagoRepository.findById(id).map(pago -> {
            pago.setTotal(pagoDetails.getTotal());
            pago.setFechaHora(pagoDetails.getFechaHora());
            pago.setTicket(pagoDetails.getTicket());
            return pagoRepository.save(pago);
        });
    }

    public Optional<Pago> obtenerPagoPorCodigo(String codigo) {
        Ticket ticket = ticketRepository.findByCodigoBarrasQR(codigo);
        if (ticket == null) {
            return Optional.empty();
        }

        return Optional.of(calcularTotal(
                ticket.getCodigoBarrasQR(),
                ticket.getVehiculo().getTipo(),
                ticket.getFechaHoraEntrada(),
                LocalDateTime.now()
        ));
    }

    public Pago calcularTotal(String codigo, String vehiculo, LocalDateTime horaEntrada, LocalDateTime horaSalida) {
        Tarifa tarifa = tarifaRepository.findByTipoVehiculo(vehiculo);
        if (tarifa == null) {
            throw new IllegalArgumentException("Tarifa no encontrada para el tipo de vehículo: " + vehiculo);
        }

        Pago pago = new Pago();
        Integer precioVehiculo = tarifa.getPrecioDia();

        long minutos = Duration.between(horaEntrada, horaSalida).toMinutes();
        long horas = (long) Math.ceil(minutos / 60.0);
        int bloques = (int) Math.ceil(horas / 12.0);

        int total = (bloques == 0) ? precioVehiculo : bloques * precioVehiculo;

        pago.setTicket(ticketRepository.findByCodigoBarrasQR(codigo));
        pago.setTotal(total);
        pago.setFechaHora(LocalDateTime.now());

        return pago;
    }
}
