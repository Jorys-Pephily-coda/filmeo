package com.filmeo.filmeo.model.repository;

import com.filmeo.filmeo.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {}
