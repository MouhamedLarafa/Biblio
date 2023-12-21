package com.example.biblio.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Integer idUser;
    String nom;
    String prenom;
    @Enumerated(EnumType.STRING)
    Role role;
    @Temporal(TemporalType.DATE)
    Date dateNaissance;
    String adresse;
    String adresseMail;
    String motDePasse;
    String numTel;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    Set<Emprunt> emprunts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    Set<Reservation> reservations;

}
