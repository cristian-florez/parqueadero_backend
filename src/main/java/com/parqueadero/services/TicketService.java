package com.parqueadero.services;

import com.parqueadero.models.Ticket;
import com.parqueadero.repositories.TicketRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Page<Ticket> buscarTodos(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    public Optional<Ticket> buscarPorId(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket guardar(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void eliminarPorId(Long id) {
        ticketRepository.deleteById(id);
    }
}