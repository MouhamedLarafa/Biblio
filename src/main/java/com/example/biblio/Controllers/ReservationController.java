package com.example.biblio.Controllers;

import com.example.biblio.Services.IReservationServices;
import com.example.biblio.entities.Emprunt;
import com.example.biblio.entities.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReservationController {
    private final IReservationServices reservationServices;

    @GetMapping("/reservationbyuser/{id}")
    public List<Reservation> getAllByUser(@PathVariable("id") Integer id){
        return reservationServices.getAllByUser(id);
    }
}
