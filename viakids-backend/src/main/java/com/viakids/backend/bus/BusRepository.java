package com.viakids.backend.bus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BusRepository extends JpaRepository<Bus, UUID> {
    Optional<Bus> findByPatente(String patente);
    boolean existsByPatente(String patente);
}
