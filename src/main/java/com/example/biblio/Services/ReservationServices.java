package com.example.biblio.Services;

import com.example.biblio.Repositories.*;
import com.example.biblio.entities.Emprunt;
import com.example.biblio.entities.Livre;
import com.example.biblio.entities.Reservation;
import com.example.biblio.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServices implements  IReservationServices {

    @Autowired
    private final IReservationRepository reservationRepository;

    @Autowired
    private final IEmpruntRepository empruntRepository;

    @Autowired
    private final IUserRepository userRepository;

    @Override
    public ResponseEntity<?> addReservation(Reservation reservation, Livre livre) {
        List<Reservation> reservations = reservationRepository.findAllByLivre(livre);
        List<Emprunt> emprunts = empruntRepository.findAllByLivreAndEtatIsFalse(livre);

        Date first = null;
        for (Emprunt e : emprunts) {
            Date endDate = e.getDateFin();
            if (endDate != null && (first == null || endDate.before(first))) {
                first = endDate;
            }
        }

        if (reservation.getDateDebut().before(first)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La premier copie disponnible est en : " + first);
        }

        for (Reservation r : reservations) {
            if (isDateRangeOverlap(r.getDateDebut(), r.getDateFin(), reservation.getDateDebut(), reservation.getDateFin())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Il existe déjà une réservation pour cette période.");
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username).orElseThrow();
            reservation.setUser(user);
            reservation.setLivre(livre);
            reservation = reservationRepository.save(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        }
        else {
            return new ResponseEntity<String>("Utilisateur non authentifié.", HttpStatus.UNAUTHORIZED);
        }

    }

    private boolean isDateRangeOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        if (startDate1 == null || endDate1 == null || startDate2 == null || endDate2 == null) {
            return false;
        }
        return startDate1.before(endDate2) && endDate1.after(startDate2);
    }



}
