package com.filmeo.filmeo.model.repository;

import com.filmeo.filmeo.model.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {}
