package com.filmeo.filmeo.model.service;

import com.filmeo.filmeo.model.entity.Genre;
import com.filmeo.filmeo.model.repository.GenreRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    public Genre getById(int id) {
        Optional<Genre> Genre = genreRepository.findById(id);
        return Genre.orElse(new Genre());
    }

    public Genre update(Genre genre) {
        return genreRepository.save(genre);
    }

    public void delete(Genre genre) {
        genreRepository.delete(genre);
    }
}
