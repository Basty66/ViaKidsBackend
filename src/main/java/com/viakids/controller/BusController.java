package com.viakids.controller;

import com.viakids.model.Bus;
import com.viakids.service.BusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {
    
    private final BusService busService;
    
    public BusController(BusService busService) {
        this.busService = busService;
    }
    
    @GetMapping
    public ResponseEntity<List<Bus>> getAllBuses() {
        return ResponseEntity.ok(busService.getAllBuses());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable String id) {
        return ResponseEntity.ok(busService.getBusById(id));
    }
    
    @PostMapping
    public ResponseEntity<Bus> createBus(@RequestBody Bus bus) {
        return ResponseEntity.ok(busService.createBus(bus));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable String id, @RequestBody Bus bus) {
        return ResponseEntity.ok(busService.updateBus(id, bus));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable String id) {
        busService.deleteBus(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}/location")
    public ResponseEntity<Bus> getBusLocation(@PathVariable String id) {
        return ResponseEntity.ok(busService.getBusById(id));
    }
    
    @PutMapping("/{id}/location")
    public ResponseEntity<Bus> updateBusLocation(@PathVariable String id, @RequestBody LocationRequest request) {
        Bus bus = busService.updateLocation(id, request.getLat(), request.getLng());
        return ResponseEntity.ok(bus);
    }
    
    public static class LocationRequest {
        private Double lat;
        private Double lng;
        
        public Double getLat() { return lat; }
        public void setLat(Double lat) { this.lat = lat; }
        public Double getLng() { return lng; }
        public void setLng(Double lng) { this.lng = lng; }
    }
}
