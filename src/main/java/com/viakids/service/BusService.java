package com.viakids.service;

import com.viakids.model.Bus;
import com.viakids.repository.BusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {
    
    private final BusRepository busRepository;
    
    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }
    
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }
    
    public Bus getBusById(String id) {
        return busRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Bus no encontrado"));
    }
    
    public Bus createBus(Bus bus) {
        if (bus.getId() == null) {
            bus.setId("B" + System.currentTimeMillis());
        }
        if (busRepository.findByPatente(bus.getPatente()).isPresent()) {
            throw new RuntimeException("La patente ya está registrada");
        }
        return busRepository.save(bus);
    }
    
    public Bus updateBus(String id, Bus busDetails) {
        Bus bus = getBusById(id);
        bus.setPatente(busDetails.getPatente());
        bus.setConductor(busDetails.getConductor());
        bus.setCapacidad(busDetails.getCapacidad());
        bus.setEstado(busDetails.getEstado());
        bus.setTiempoEstimado(busDetails.getTiempoEstimado());
        bus.setLat(busDetails.getLat());
        bus.setLng(busDetails.getLng());
        return busRepository.save(bus);
    }
    
    public void deleteBus(String id) {
        Bus bus = getBusById(id);
        busRepository.delete(bus);
    }
    
    public Bus updateLocation(String id, Double lat, Double lng) {
        Bus bus = getBusById(id);
        bus.setLat(lat);
        bus.setLng(lng);
        return busRepository.save(bus);
    }
}
