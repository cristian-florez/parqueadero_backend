package com.parqueadero.services;

import com.parqueadero.models.CierreTurno;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CierreTurnoSpecification {

    public static Specification<CierreTurno> fechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin) {
        return (root, query, criteriaBuilder) -> {
            if (inicio == null && fin == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // No filter
            }
            if (inicio != null && fin != null) {
                return criteriaBuilder.between(root.get("fechaCreacion"), inicio, fin);
            }
            if (inicio != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("fechaCreacion"), inicio);
            }
            // fin != null
            return criteriaBuilder.lessThanOrEqualTo(root.get("fechaCreacion"), fin);
        };
    }

    public static Specification<CierreTurno> hasNombreUsuario(String nombreUsuario) {
        return (root, query, criteriaBuilder) -> {
            if (nombreUsuario == null || nombreUsuario.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("nombreUsuario")), "%" + nombreUsuario.toLowerCase() + "%");
        };
    }
}
