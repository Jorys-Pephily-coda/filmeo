package com.filmeo.filmeo.model.service;

import com.filmeo.filmeo.model.entity.ArtistReview;
import com.filmeo.filmeo.model.repository.ArtistReviewRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistReviewService {

    @Autowired
    private ArtistReviewRepository artistReviewRepository;

    public List<ArtistReview> getAll() {
        return artistReviewRepository.findAll();
    }

    public ArtistReview getById(int id) {
        Optional<ArtistReview> ArtistReview = artistReviewRepository.findById(
            id
        );
        return ArtistReview.orElse(new ArtistReview());
    }

    public ArtistReview update(ArtistReview artistReview) {
        return artistReviewRepository.save(artistReview);
    }

    public void delete(ArtistReview artistReview) {
        artistReviewRepository.delete(artistReview);
    }
}
