package com.filmeo.filmeo.model.repository;

import com.filmeo.filmeo.model.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository
    extends JpaRepository<Production, Integer> {}
