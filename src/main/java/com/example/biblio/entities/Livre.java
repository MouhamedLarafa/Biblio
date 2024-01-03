package com.example.biblio.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Livre implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Integer idLivre;

    String titre;

    Integer isbn;

    String nomAuteur;

    Integer nbExemplaire;

    Integer nbExemplaireEmprinte;

    byte[] image;

    @Temporal(TemporalType.DATE)
    Date datePublication;

    @OneToMany(mappedBy = "livre")
    @JsonIgnore
    Set<Emprunt> emprunt;

    @ManyToOne
    Categorie categorie;


}
