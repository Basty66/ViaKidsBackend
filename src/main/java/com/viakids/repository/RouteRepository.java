package com.viakids.repository;

import com.viakids.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {
    List<Route> findByBusId(String busId);
    List<Route> findByColegio(String colegio);
}
