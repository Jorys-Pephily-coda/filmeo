package com.filmeo.filmeo.model.repository;

import com.filmeo.filmeo.model.entity.ProductionReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionReviewRepository
    extends JpaRepository<ProductionReview, Integer> {}
