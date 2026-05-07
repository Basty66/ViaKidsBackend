package com.viakids.repository;

import com.viakids.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, String> {
    Optional<Bus> findByPatente(String patente);
}
