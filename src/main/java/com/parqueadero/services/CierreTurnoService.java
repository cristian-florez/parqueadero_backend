package com.parqueadero.services;

import com.parqueadero.models.CierreTurno;
import com.parqueadero.repositories.CierreTurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CierreTurnoService {

    @Autowired
    private CierreTurnoRepository cierreTurnoRepository;

    public CierreTurno guardarCierreTurno(CierreTurno cierreTurno) {
        return cierreTurnoRepository.save(cierreTurno);
    }

    public Page<CierreTurno> obtenerTodosLosCierres(Pageable pageable, LocalDateTime inicio, LocalDateTime fin, String nombreUsuario) {
        Specification<CierreTurno> spec = Specification.where(CierreTurnoSpecification.fechaCreacionBetween(inicio, fin))
                                                    .and(CierreTurnoSpecification.hasNombreUsuario(nombreUsuario));
        return cierreTurnoRepository.findAll(spec, pageable);
    }

    public Optional<CierreTurno> obtenerCierrePorId(Long id) {
        return cierreTurnoRepository.findById(id);
    }
}
