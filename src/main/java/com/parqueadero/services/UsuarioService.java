package com.parqueadero.services;

import com.parqueadero.models.Usuario;
import com.parqueadero.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    public Usuario login(String nombre, String cedula) {
        return usuarioRepository.findByNombreAndCedula(nombre, cedula);
    }
}
