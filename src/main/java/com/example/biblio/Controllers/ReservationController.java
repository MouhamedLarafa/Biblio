package com.example.biblio.Controllers;

import com.example.biblio.Repositories.IUserRepository;
import com.example.biblio.Services.IReservationServices;
import com.example.biblio.entities.Emprunt;
import com.example.biblio.entities.Reservation;
import com.example.biblio.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReservationController {
    private final IReservationServices reservationServices;
    private final IUserRepository userRepository;

    @GetMapping("/reservationbyuser/{id}")
    public List<Reservation> getAllByUser(@PathVariable("id") Integer id){
        return reservationServices.getAllByUser(id);
    }

    @GetMapping("/reservationsuser")
    public List<Reservation> getReservationsUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return reservationServices.getAllByUser(u.getIdUser());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addReservation(@RequestBody Reservation reservation,
                                            @PathVariable("id") Integer idLivre) {
            return reservationServices.addReservation(reservation, idLivre);
    }

    @DeleteMapping("/deletereservation/{id}")
    public void deletereservation(@PathVariable("id") Integer idReservation){
        reservationServices.deleteReservation(idReservation);
    }

    @GetMapping("/all")
    public List<Reservation> retrieveAll() {
        return reservationServices.retrieveAll();
    }


    }
