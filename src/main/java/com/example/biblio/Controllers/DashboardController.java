package com.example.biblio.Controllers;

import com.example.biblio.Services.IStatistiqueServices;
import com.example.biblio.Services.IUserServices;
import com.example.biblio.entities.Livre;
import com.example.biblio.entities.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stat")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DashboardController {
    private final IStatistiqueServices statistiqueServices;

    @GetMapping("/nbEmprunt")
    public Integer getNbEmprunt() {
        return statistiqueServices.nbEmprunt();
    }

    @GetMapping("/nbLivreTopEmprunt")
    public Map<Livre, Integer> getNbLivreTopEmprunt() {
        return statistiqueServices.nbLivreTopEmprunt();
    }

    @GetMapping("/nbEmpruntMois")
    public Integer getNbEmpruntMois() {
        return statistiqueServices.nbEmpruntMois();
    }

    @GetMapping("/nbReservationMois")
    public Integer getNbReservationMois() {
        return statistiqueServices.nbReservationMois();
    }

    @GetMapping("/nbReservationEnAttente")
    public Integer getNbReservationEnAttente() {
        return statistiqueServices.nbReservationEnAttente();
    }

    @GetMapping("/nbLivreTotale")
    public Integer getNbLivreTotale() {
        return statistiqueServices.nbLivreTotale();
    }

    @GetMapping("/booksAndCopies")
    public Map<String, Map<String, Integer>> getBooksAndCopies() {
        return statistiqueServices.getBooksAndCopies();
    }

    @GetMapping("/nbReservationEnAttenteParBook")
    public Map<Livre, List<Reservation>> getNbReservationEnAttenteParBook() {
        return statistiqueServices.getNbReservationEnAttenteParBook();
    }

}
