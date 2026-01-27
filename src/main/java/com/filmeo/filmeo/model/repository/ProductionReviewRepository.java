package com.filmeo.filmeo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filmeo.filmeo.model.entity.ProductionReview;

@Repository
public interface ProductionReviewRepository extends JpaRepository<ProductionReview, Integer> {}