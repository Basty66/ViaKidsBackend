package com.viakids.backend.bus;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BusService {

    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public List<Bus> getAll() {
        return busRepository.findAll();
    }

    public Bus getById(UUID id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus no encontrado"));
    }

    public Bus getByPatente(String patente) {
        return busRepository.findByPatente(patente)
                .orElseThrow(() -> new RuntimeException("Bus no encontrado"));
    }

    public Bus create(Bus bus) {
        bus.setId(null);
        if (busRepository.existsByPatente(bus.getPatente())) {
            throw new RuntimeException("La patente ya existe");
        }
        if (bus.getEstado() == null) bus.setEstado(Bus.BusStatus.EN_ESPERA);
        if (bus.getCapacidad() == null) bus.setCapacidad(40);
        return busRepository.save(bus);
    }

    public Bus update(UUID id, Bus updates) {
        Bus bus = getById(id);
        if (!bus.getPatente().equals(updates.getPatente()) &&
                busRepository.existsByPatente(updates.getPatente())) {
            throw new RuntimeException("La patente ya existe en otro registro");
        }
        bus.setPatente(updates.getPatente());
        bus.setConductor(updates.getConductor());
        bus.setCapacidad(updates.getCapacidad());
        bus.setEstado(updates.getEstado());
        bus.setTiempoEstimado(updates.getEstado() == Bus.BusStatus.EN_ESPERA ? "--" : updates.getTiempoEstimado());
        bus.setLat(updates.getLat());
        bus.setLng(updates.getLng());
        return busRepository.save(bus);
    }

    public void delete(UUID id) {
        busRepository.deleteById(id);
    }

    public Bus updateLocation(UUID id, Double lat, Double lng) {
        Bus bus = getById(id);
        bus.setLat(lat);
        bus.setLng(lng);
        return busRepository.save(bus);
    }
}
