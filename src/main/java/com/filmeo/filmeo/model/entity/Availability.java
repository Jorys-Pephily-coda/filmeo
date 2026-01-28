package com.filmeo.filmeo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Date;
import lombok.Data;

@Entity
@Data
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "streaming_platform_id")
    private StreamingPlatform streamingPlatform;

    @ManyToOne
    @JoinColumn(name = "production_id")
    private Production production;

    private Date expiratonDate;
}
