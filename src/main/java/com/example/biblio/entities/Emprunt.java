package com.example.biblio.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Emprunt implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idEmprunt;

    @Temporal(TemporalType.DATE)
    Date dateDebut;

    @Temporal(TemporalType.DATE)
    Date dateFin;

    boolean etat = false; // Mazelou ma raj3oouhesh

    @ManyToOne
    User user;

    @ManyToOne
    Livre livre;
}
