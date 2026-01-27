package com.filmeo.filmeo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.filmeo.filmeo.model.entity.ProductionReview;
import com.filmeo.filmeo.model.repository.ProductionReviewRepository;

public class ProductionReviewService {
    @Autowired
    private ProductionReviewRepository productionReviewRepository;

    public List<ProductionReview> getAll() {
        return productionReviewRepository.findAll();
    }


    public ProductionReview getById(int id) {
        Optional<ProductionReview> ProductionReview = productionReviewRepository.findById(id);
        return ProductionReview.orElse(new ProductionReview());
    }

    public ProductionReview update(ProductionReview productionReview) {
        return productionReviewRepository.save(productionReview);
    }

    public void delete(ProductionReview productionReview) {
        productionReviewRepository.delete(productionReview);
    }   
    
}
