package com.viakids.backend.route;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public java.util.List<Route> getAll() {
        return routeService.getAll();
    }

    @GetMapping("/{id}")
    public Route getById(@PathVariable UUID id) {
        return routeService.getById(id);
    }

    @PostMapping
    public Route create(@RequestBody Route route) {
        return routeService.create(route);
    }

    @PutMapping("/{id}")
    public Route update(@PathVariable UUID id, @RequestBody Route route) {
        return routeService.update(id, route);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        routeService.delete(id);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
