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
    Date dateReservation = new Date(System.currentTimeMillis());

    @Temporal(TemporalType.DATE)
    Date dateDebut;

    @Temporal(TemporalType.DATE)
    Date dateFin;

    boolean prise = false; // Mazelou ma khdhewhehsh yaani mazelet juste reservation

    @ManyToOne
    User user;

    @OneToOne
    Livre livre;

}
