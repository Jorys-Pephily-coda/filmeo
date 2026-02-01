package com.filmeo.filmeo.model.service;

import com.filmeo.filmeo.model.entity.Availability;
import com.filmeo.filmeo.model.repository.AvailabilityRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    public List<Availability> getAll() {
        return availabilityRepository.findAll();
    }

    public Availability getById(int id) {
        Optional<Availability> Availability = availabilityRepository.findById(
            id
        );
        return Availability.orElse(new Availability());
    }

    public Availability update(Availability availability) {
        return availabilityRepository.save(availability);
    }

    public void delete(Availability availability) {
        availabilityRepository.delete(availability);
    }
}
