package com.filmeo.filmeo.model.repository;

import com.filmeo.filmeo.model.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository
    extends JpaRepository<Availability, Integer> {}
