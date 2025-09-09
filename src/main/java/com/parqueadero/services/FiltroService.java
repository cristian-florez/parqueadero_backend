package com.parqueadero.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parqueadero.dtos.filtros.FiltrosDTO;
import com.parqueadero.repositories.ParqueaderoRepository;
import com.parqueadero.repositories.TarifaRepository;
import com.parqueadero.repositories.UsuarioRepository;

@Service
public class FiltroService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private ParqueaderoRepository parqueaderoRepository;

    public FiltrosDTO getFiltros() {
        List<String> usuarios = usuarioRepository.findDistinctNombres();
        List<String> tiposVehiculo = tarifaRepository.findDistinctTipoVehiculo();
        List<String> parqueaderos = parqueaderoRepository.findDistinctNombres();

        return new FiltrosDTO(usuarios, tiposVehiculo, parqueaderos);
    }
}
