package com.parqueadero.services;

import com.parqueadero.models.CierreTurno;
import com.parqueadero.repositories.CierreTurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CierreTurnoService {

    @Autowired
    private CierreTurnoRepository cierreTurnoRepository;

    public CierreTurno guardarCierreTurno(CierreTurno cierreTurno) {
        return cierreTurnoRepository.save(cierreTurno);
    }

    public List<CierreTurno> obtenerTodosLosCierres() {
        return cierreTurnoRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaCreacion"));
    }

    public Optional<CierreTurno> obtenerCierrePorId(Long id) {
        return cierreTurnoRepository.findById(id);
    }
}
