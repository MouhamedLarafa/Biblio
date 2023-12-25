package com.example.biblio.Services;

import com.example.biblio.entities.Livre;
import com.example.biblio.entities.Reservation;
import org.springframework.http.ResponseEntity;

public interface IReservationServices {
    public ResponseEntity<?> addReservation(Reservation reservation, Livre livre);

}
