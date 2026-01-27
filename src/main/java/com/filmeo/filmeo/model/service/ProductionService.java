package com.filmeo.filmeo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.filmeo.filmeo.model.entity.Production;
import com.filmeo.filmeo.model.repository.ProductionRepository;

public class ProductionService {
    @Autowired
    private ProductionRepository productionRepository;

    public List<Production> getAll() {
        return productionRepository.findAll();
    }


    public Production getById(int id) {
        Optional<Production> Production = productionRepository.findById(id);
        return Production.orElse(new Production());
    }

    public Production update(Production production) {
        return productionRepository.save(production);
    }

    public void delete(Production production) {
        productionRepository.delete(production);
    }   
    
}
