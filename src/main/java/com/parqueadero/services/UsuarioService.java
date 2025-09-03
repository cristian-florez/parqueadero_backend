package com.parqueadero.services;

import com.parqueadero.models.Usuario;
import com.parqueadero.models.DTOS.TicketCierreTurno;
import com.parqueadero.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TicketService ticketService;

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminarPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> actualizarUsuario(Long id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setCedula(usuarioDetails.getCedula());
            return usuarioRepository.save(usuario);
        });
    }

    public Optional<Usuario> login(String nombre, String cedula) {
        Usuario usuario = usuarioRepository.findByNombreAndCedula(nombre, cedula);
        if (usuario != null) {
            usuario.setFechaInicioSesion(LocalDateTime.now());
            usuarioRepository.save(usuario);
            return Optional.of(usuario);
        }
        return Optional.empty();
    }

    public TicketCierreTurno cerrarTurno(LocalDateTime fechaInicio, LocalDateTime fechaCierre) {
        return ticketService.ticketCierreTurno(fechaInicio, fechaCierre);
    }
}
