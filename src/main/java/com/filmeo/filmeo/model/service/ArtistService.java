package com.filmeo.filmeo.model.service;

import com.filmeo.filmeo.model.entity.Artist;
import com.filmeo.filmeo.model.repository.ArtistRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getAll() {
        return artistRepository.findAll();
    }

    public Artist getById(int id) {
        Optional<Artist> Artist = artistRepository.findById(id);
        return Artist.orElse(new Artist());
    }

    public Artist update(Artist artist) {
        return artistRepository.save(artist);
    }

    public void delete(Artist artist) {
        artistRepository.delete(artist);
    }
}
