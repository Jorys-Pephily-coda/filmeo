package com.filmeo.filmeo.model.repository;

import com.filmeo.filmeo.model.entity.ArtistReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistReviewRepository
    extends JpaRepository<ArtistReview, Integer> {}
