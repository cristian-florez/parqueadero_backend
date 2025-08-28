package com.parqueadero.services;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.parqueadero.models.Ticket;

public class TicketSpecification {

    public static Specification<Ticket> hasCodigo(String codigo) {
        return (root, query, criteriaBuilder) -> {
            if (codigo == null || codigo.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("codigoBarrasQR")), "%" + codigo.toLowerCase() + "%");
        };
    }

    public static Specification<Ticket> hasPlaca(String placa) {
        return (root, query, criteriaBuilder) -> {
            if (placa == null || placa.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("vehiculo").get("placa")), "%" + placa.toLowerCase() + "%");
        };
    }

    public static Specification<Ticket> hasTipo(String tipo) {
        return (root, query, criteriaBuilder) -> {
            if (tipo == null || tipo.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("vehiculo").get("tipo")), tipo.toLowerCase());
        };
    }

    public static Specification<Ticket> hasUsuarioRecibio(String usuarioRecibio) {
        return (root, query, criteriaBuilder) -> {
            if (usuarioRecibio == null || usuarioRecibio.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("usuarioRecibio")), "%" + usuarioRecibio.toLowerCase() + "%");
        };
    }

    public static Specification<Ticket> hasUsuarioEntrego(String usuarioEntrego) {
        return (root, query, criteriaBuilder) -> {
            if (usuarioEntrego == null || usuarioEntrego.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("usuarioEntrego")), "%" + usuarioEntrego.toLowerCase() + "%");
        };
    }

    public static Specification<Ticket> isPagado(Boolean pagado) {
        return (root, query, criteriaBuilder) -> {
            if (pagado == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("pagado"), pagado);
        };
    }

    public static Specification<Ticket> fechaEntradaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return (root, query, criteriaBuilder) -> {
            if (fechaInicio != null && fechaFin != null) {
                return criteriaBuilder.between(root.get("fechaHoraEntrada"), fechaInicio, fechaFin);
            } else if (fechaInicio != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("fechaHoraEntrada"), fechaInicio);
            } else if (fechaFin != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("fechaHoraEntrada"), fechaFin);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
