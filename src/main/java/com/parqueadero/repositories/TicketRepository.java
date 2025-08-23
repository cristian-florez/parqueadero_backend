package com.parqueadero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parqueadero.models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findByCodigoBarrasQR(String codigoBarras);

    Ticket findByCodigoBarrasQRAndPagado(String codigoBarras, boolean pagado);

}
