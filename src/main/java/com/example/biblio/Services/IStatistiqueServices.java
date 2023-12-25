package com.example.biblio.Services;

import com.example.biblio.entities.Livre;
import com.example.biblio.entities.Reservation;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;
import java.util.Map;

public interface IStatistiqueServices {

    public Integer nbEmprunt();

    public Map<Livre,Integer> nbLivreTopEmprunt();

    public Integer nbEmpruntMois();

    public Integer nbReservationMois();

    public Integer nbReservationEnAttente();

    public Integer nbLivreTotale();

    public Map<String, Map<String, Integer>> getBooksAndCopies();

    public Map<Livre, List<Reservation>> getNbReservationEnAttenteParBook();



}
