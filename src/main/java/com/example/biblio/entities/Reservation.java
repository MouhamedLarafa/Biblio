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
public class Reservation implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Integer idReservation;

    @Temporal(TemporalType.DATE)
    Date dateReservation;

    @Temporal(TemporalType.DATE)
    Date dateDebut;

    @Temporal(TemporalType.DATE)
    Date dateFin;

    @ManyToOne
    User user;

    @OneToOne
    Livre livre;

}
