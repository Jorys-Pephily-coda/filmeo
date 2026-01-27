package com.filmeo.filmeo.model.entity;

import java.util.ArrayList;
import java.util.List;

//import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private List<String> roles = new ArrayList<String>();

    public void addRole(String role) {
        this.roles.add(role);
    }

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    //@NotNull(message = "Le mot de passe ne peut pas être nul")
    //@NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;

    @OneToOne
    @JoinColumn(name = "nationality_id")
    private Country nationality;

    @ManyToMany
    @JoinTable(name = "watch_list",
                joinColumns = { @JoinColumn(name = "user_id") },
                inverseJoinColumns = { @JoinColumn(name = "production_id") }
              )
    public List<Production> watchList;
}    