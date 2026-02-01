package com.filmeo.filmeo.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;
import lombok.Data;

@Entity
@Data
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, unique = true)
    private String title;

    private String posterUrl;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Artist director;

    @ManyToOne
    @JoinColumn(name = "nationality_id")
    private Country producingCountry;

    @ManyToMany
    @JoinTable(
        name = "casting",
        joinColumns = { @JoinColumn(name = "production_id") },
        inverseJoinColumns = { @JoinColumn(name = "artist_id") }
    )
    public List<Artist> artists;

    @ManyToMany
    @JoinTable(
        name = "production_genre",
        joinColumns = { @JoinColumn(name = "production_id") },
        inverseJoinColumns = { @JoinColumn(name = "genre_id") }
    )
    public List<Genre> genres;

    @OneToMany(mappedBy = "production")
    private List<Availability> availabilities;

    @OneToMany(mappedBy = "production")
    private List<ProductionReview> reviews;

    @OneToOne(mappedBy = "production", cascade = CascadeType.ALL)
    private Series seriesDetails; 
}
