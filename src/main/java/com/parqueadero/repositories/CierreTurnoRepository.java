package com.parqueadero.repositories;

import com.parqueadero.models.CierreTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CierreTurnoRepository extends JpaRepository<CierreTurno, Long>, JpaSpecificationExecutor<CierreTurno> {
}
