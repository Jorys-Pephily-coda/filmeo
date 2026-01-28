package com.filmeo.filmeo.model.repository;

import com.filmeo.filmeo.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {}
