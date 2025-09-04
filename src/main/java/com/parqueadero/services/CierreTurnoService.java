package com.parqueadero.services;

import com.parqueadero.dtos.cierreTurno.TicketCierreTurno;
import com.parqueadero.dtos.vehiculos.TotalVehiculosDTO;
import com.parqueadero.models.CierreTurno;
import com.parqueadero.models.Usuario;
import com.parqueadero.repositories.CierreTurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CierreTurnoService {

    @Autowired
    private CierreTurnoRepository cierreTurnoRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioService usuarioService;

    // logica para crear un ticket de cierre de turno
    public TicketCierreTurno ticketCierreTurno(long idUsuarioLogueado) {

        Usuario usuario = usuarioService.buscarPorId(idUsuarioLogueado);

        LocalDateTime fechaInicio = usuario.getFechaInicioSesion();
        LocalDateTime fechaCierre = LocalDateTime.now();

        TicketCierreTurno cierreTurno = ticketService.generarDatosCierre(fechaInicio, fechaCierre);

        return cierreTurno;
    }

    @Transactional
    public TicketCierreTurno crearYGuardarCierre(long idUsuarioLogueado) {

        TicketCierreTurno dto = ticketCierreTurno(idUsuarioLogueado);
        Usuario usuario = usuarioService.buscarPorId(idUsuarioLogueado);
        if (usuario == null) {
            throw new NoSuchElementException("Usuario en ticket cierre con ID " + idUsuarioLogueado + " no encontrado");
        }

        // mapeamos el ticketCierre con el modelo para poder crear registro en la bd
        CierreTurno nuevoCierre = new CierreTurno();

        if(dto.getFechaInicio() == null) {
            throw new NoSuchElementException("el usuario no registra fecha de inicio de turno");
        }
        nuevoCierre.setFechaInicioTurno(dto.getFechaInicio());
        nuevoCierre.setFechaFinTurno(dto.getFechaCierre());
        nuevoCierre.setTotalIngresos(dto.getTotalAPagar());
        nuevoCierre.setDetalleEntrantes(formatearDetalle(dto.getListaTiposVehiculosEntrantes()));
        nuevoCierre.setDetalleSalientes(formatearDetalle(dto.getListaTiposVehiculosSalientes()));
        nuevoCierre.setDetalleRestantes(formatearDetalle(dto.getListaTiposVehiculosParqueadero()));
        nuevoCierre.setNombreUsuario(usuario.getNombre());

        usuarioService.eliminarFechaInicioSesion(usuario);


        cierreTurnoRepository.save(nuevoCierre);

        // Retornar DTO (para frontend)
        return dto;
    }

    public Page<CierreTurno> obtenerTodosLosCierres(Pageable pageable, LocalDateTime inicio, LocalDateTime fin,
                                                    String nombreUsuario) {
        Specification<CierreTurno> spec = Specification
                .where(CierreTurnoSpecification.fechaCreacionBetween(inicio, fin))
                .and(CierreTurnoSpecification.hasNombreUsuario(nombreUsuario));
        return cierreTurnoRepository.findAll(spec, pageable);
    }

    public CierreTurno obtenerCierrePorId(Long id) {
        return cierreTurnoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("CierreTurno con ID " + id + " no encontrado"));
    }

    // este metodo se creo para que la lista que obtengo de mi ticketCierre
    // lo pueda convertir en un string y poder guardarlo en la base de datos
    private String formatearDetalle(List<TotalVehiculosDTO> detalle) {
        if (detalle == null || detalle.isEmpty()) {
            return "";
        }
        return detalle.stream()
                .map(dto -> dto.getTipo() + ": " + dto.getCantidad())
                .collect(Collectors.joining(", "));
    }
}
