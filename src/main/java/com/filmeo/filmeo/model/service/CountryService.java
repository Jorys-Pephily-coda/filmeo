package com.filmeo.filmeo.model.service;

import com.filmeo.filmeo.model.entity.Country;
import com.filmeo.filmeo.model.repository.CountryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    public Country getById(int id) {
        Optional<Country> Country = countryRepository.findById(id);
        return Country.orElse(new Country());
    }

    public Country update(Country country) {
        return countryRepository.save(country);
    }

    public void delete(Country country) {
        countryRepository.delete(country);
    }
}
