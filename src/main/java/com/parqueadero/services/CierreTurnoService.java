package com.parqueadero.services;

import com.parqueadero.models.CierreTurno;
import com.parqueadero.models.DTOS.TicketCierreTurno;
import com.parqueadero.repositories.CierreTurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CierreTurnoService {

    @Autowired
    private CierreTurnoRepository cierreTurnoRepository;

    @Autowired
    private TicketService ticketService;

    public TicketCierreTurno crearYGuardarCierre(LocalDateTime inicio, LocalDateTime fin, String nombreUsuario) {
        // 1. Calcular el DTO
        TicketCierreTurno dto = ticketService.ticketCierreTurno(inicio, fin);

        // 2. Mapear DTO a entidad
        CierreTurno nuevoCierre = new CierreTurno();
        nuevoCierre.setFechaCreacion(LocalDateTime.now());
        nuevoCierre.setFechaInicioTurno(inicio);
        nuevoCierre.setFechaFinTurno(fin != null ? fin : LocalDateTime.now());
        nuevoCierre.setNombreUsuario(nombreUsuario);
        nuevoCierre.setTotalIngresos(dto.getTotalAPagar());

        if (dto.getTotalVehiculosQueEntraron() != null) {
            nuevoCierre.setTotalVehiculosEntraron(dto.getTotalVehiculosQueEntraron().size());
        }
        if (dto.getTotalVehiculosQueSalieron() != null) {
            nuevoCierre.setTotalVehiculosSalieron(dto.getTotalVehiculosQueSalieron().size());
        }
        if (dto.getVehiculosEnParqueadero() != null) {
            nuevoCierre.setVehiculosRestantes(dto.getVehiculosEnParqueadero().size());
        }

        nuevoCierre.setDetalleEntrantes(formatDetalle(dto.getTipoVehiculosEntrantes()));
        nuevoCierre.setDetalleSalientes(formatDetalle(dto.getTipoVehiculosSaliente()));
        nuevoCierre.setDetalleRestantes(formatDetalle(dto.getTipoVehiculosParqueadero()));

        // 3. Guardar
        cierreTurnoRepository.save(nuevoCierre);

        // 4. Retornar DTO (para frontend)
        return dto;
    }

    public Page<CierreTurno> obtenerTodosLosCierres(Pageable pageable, LocalDateTime inicio, LocalDateTime fin, String nombreUsuario) {
        Specification<CierreTurno> spec = Specification.where(CierreTurnoSpecification.fechaCreacionBetween(inicio, fin))
                                                    .and(CierreTurnoSpecification.hasNombreUsuario(nombreUsuario));
        return cierreTurnoRepository.findAll(spec, pageable);
    }

    public Optional<CierreTurno> obtenerCierrePorId(Long id) {
        return cierreTurnoRepository.findById(id);
    }

    // Helper para formatear los detalles
    private String formatDetalle(List<Object> detalle) {
        if (detalle == null || detalle.isEmpty()) {
            return "";
        }
        return detalle.stream()
                .map(item -> {
                    if (item instanceof Object[]) {
                        Object[] array = (Object[]) item;
                        if (array.length >= 2) {
                            return array[0].toString() + ": " + array[1].toString();
                        }
                    }
                    return item.toString();
                })
                .collect(Collectors.joining(", "));
    }
}
