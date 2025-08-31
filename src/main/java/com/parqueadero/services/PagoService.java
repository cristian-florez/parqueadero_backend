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

            // Precio base por bloque (en tu caso, 3000 por 12 horas)
            Integer precioVehiculo = tarifa.getPrecioDia();

            // Calculamos la diferencia en minutos (no solo horas)
            long minutos = java.time.Duration.between(horaEntrada, horaSalida).toMinutes();

            // Convertimos minutos a horas, redondeando hacia arriba si hay algún minuto extra
            long horas = (long) Math.ceil(minutos / 60.0);

            // Dividimos en bloques de 12 horas, redondeando hacia arriba
            int bloques = (int) Math.ceil(horas / 12.0);

            // Si por alguna razón el tiempo es menor a 1 bloque, cobramos 1 bloque mínimo
            if (bloques == 0) {
                total = precioVehiculo;
            } else {
                total = bloques * precioVehiculo;
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