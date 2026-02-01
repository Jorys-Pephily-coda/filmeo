package com.filmeo.filmeo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;

    private String lastname;

    private String pictureUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deathDate;

    private String gender;

    @ManyToOne
    @JoinColumn(name = "nationality_id")
    private Country nationality;

    @ManyToMany
    @JoinTable(
        name = "casting",
        joinColumns = { @JoinColumn(name = "artist_id") },
        inverseJoinColumns = { @JoinColumn(name = "production_id") }
    )
    public List<Production> productions;

    @OneToMany(mappedBy = "artist")
    private List<ArtistReview> reviews;
}
