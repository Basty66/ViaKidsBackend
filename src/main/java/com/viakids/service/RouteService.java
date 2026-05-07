package com.viakids.service;

import com.viakids.model.Route;
import com.viakids.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    
    private final RouteRepository routeRepository;
    
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }
    
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }
    
    public Route getRouteById(String id) {
        return routeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
    }
    
    public Route createRoute(Route route) {
        if (route.getId() == null) {
            route.setId("R" + System.currentTimeMillis());
        }
        return routeRepository.save(route);
    }
    
    public Route updateRoute(String id, Route routeDetails) {
        Route route = getRouteById(id);
        route.setNombre(routeDetails.getNombre());
        route.setColegio(routeDetails.getColegio());
        route.setBusId(routeDetails.getBusId());
        route.setHorario(routeDetails.getHorario());
        route.setParadas(routeDetails.getParadas());
        return routeRepository.save(route);
    }
    
    public void deleteRoute(String id) {
        Route route = getRouteById(id);
        routeRepository.delete(route);
    }
}
