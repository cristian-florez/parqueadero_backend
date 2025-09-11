package com.parqueadero.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.parqueadero.dtos.cierreTurno.TicketCierreTurnoResponse;
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
import java.util.NoSuchElementException;

@Service
public class CierreTurnoService {

    @Autowired
    private CierreTurnoRepository cierreTurnoRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioService usuarioService;

    // logica para crear un ticket de cierre de turno
    public TicketCierreTurnoResponse ticketCierreTurno(long idUsuarioLogueado) {

        Usuario usuario = usuarioService.buscarPorId(idUsuarioLogueado);

        LocalDateTime fechaInicio = usuario.getFechaInicioSesion();
        LocalDateTime fechaCierre = LocalDateTime.now();

        TicketCierreTurnoResponse cierreTurno = ticketService.generarDatosCierre(fechaInicio, fechaCierre);

        return cierreTurno;
    }

    @Transactional
    public TicketCierreTurnoResponse crearYGuardarCierre(long idUsuarioLogueado) {

        TicketCierreTurnoResponse dto = ticketCierreTurno(idUsuarioLogueado);
        Usuario usuario = usuarioService.buscarPorId(idUsuarioLogueado);
        if (usuario == null) {
            throw new NoSuchElementException("Usuario en ticket cierre con ID " + idUsuarioLogueado + " no encontrado");
        }

        if (dto.getFechaInicio() == null) {
            throw new NoSuchElementException("el usuario no registra fecha de inicio de turno");
        }

        dto.setNombreUsuario(usuario.getNombre());

        CierreTurno nuevoCierre = new CierreTurno();
        nuevoCierre.setFechaInicioTurno(dto.getFechaInicio());
        nuevoCierre.setFechaFinTurno(dto.getFechaCierre());
        nuevoCierre.setNombreUsuario(usuario.getNombre());
        nuevoCierre.setTotalIngresos(dto.getTotal());
        nuevoCierre.setDetallesJson(convertirDetallesAJson(dto));

        usuarioService.eliminarFechaInicioSesion(usuario);

        cierreTurnoRepository.save(nuevoCierre);

        return dto;
    }


    public Page<CierreTurno> obtenerTodosLosCierres(Pageable pageable, LocalDateTime inicio, LocalDateTime fin,
                                                    String nombreUsuario) {

        Specification<CierreTurno> spec = Specification.allOf(
                CierreTurnoSpecification.fechaCreacionBetween(inicio, fin),
                CierreTurnoSpecification.hasNombreUsuario(nombreUsuario)
        );

        return cierreTurnoRepository.findAll(spec, pageable);
    }

    public CierreTurno obtenerCierrePorId(Long id) {
        return cierreTurnoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("CierreTurno con ID " + id + " no encontrado"));
    }

    // este metodo se creo para que la lista que obtengo de mi ticketCierre
    //lo puedo convertir en un json
    private String convertirDetallesAJson(TicketCierreTurnoResponse dto) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Módulo necesario para que Jackson maneje correctamente fechas como LocalDateTime
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(dto.getDetallesPorParqueadero());
        } catch (JsonProcessingException e) {
            return "{\"error\":\"No se pudo serializar el detalle del cierre\"}";
        }
    }
}
