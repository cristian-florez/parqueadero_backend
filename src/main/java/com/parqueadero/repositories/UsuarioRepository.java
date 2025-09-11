package com.parqueadero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parqueadero.models.Usuario;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByNombreAndCedula(String nombre, String cedula);

    @Query("SELECT DISTINCT u.nombre FROM Usuario u")
    List<String> findDistinctNombres();

    boolean existsByCedulaNotAndFechaInicioSesionIsNotNull(String cedula);
}
