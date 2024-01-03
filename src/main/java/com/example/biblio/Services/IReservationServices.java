package com.example.biblio.Services;

import com.example.biblio.entities.Emprunt;
import com.example.biblio.entities.Livre;
import com.example.biblio.entities.Reservation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReservationServices {
    public ResponseEntity<?> addReservation(Reservation reservation, Livre livre);
    public List<Reservation> getAllByUser(int idUser);


}
