package com.viakids.backend.route;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getAll() {
        return routeRepository.findAll();
    }

    public Route getById(UUID id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
    }

    public Route create(Route route) {
        route.setId(null);
        if (route.getParadas() == null) route.setParadas(0);
        return routeRepository.save(route);
    }

    public Route update(UUID id, Route updates) {
        Route route = getById(id);
        route.setNombre(updates.getNombre());
        route.setColegio(updates.getColegio());
        route.setBusId(updates.getBusId());
        route.setHorario(updates.getHorario());
        route.setParadas(updates.getParadas());
        return routeRepository.save(route);
    }

    public void delete(UUID id) {
        routeRepository.deleteById(id);
    }
}
