package com.viakids.backend.bus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    public java.util.List<Bus> getAll() {
        return busService.getAll();
    }

    @GetMapping("/{id}")
    public Bus getById(@PathVariable UUID id) {
        return busService.getById(id);
    }

    @PostMapping
    public Bus create(@RequestBody Bus bus) {
        return busService.create(bus);
    }

    @PutMapping("/{id}")
    public Bus update(@PathVariable UUID id, @RequestBody Bus bus) {
        return busService.update(id, bus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        busService.delete(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<?> getLocation(@PathVariable UUID id) {
        Bus bus = busService.getById(id);
        return ResponseEntity.ok(Map.of("lat", bus.getLat(), "lng", bus.getLng()));
    }
}
