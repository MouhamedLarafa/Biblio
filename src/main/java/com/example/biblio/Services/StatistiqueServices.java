package com.example.biblio.Services;

import com.example.biblio.Repositories.*;
import com.example.biblio.entities.Livre;
import com.example.biblio.entities.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatistiqueServices implements IStatistiqueServices{

    @Autowired
    private final IEmpruntRepository empruntRepository;

    @Autowired
    private final ICategorieRepository categorieRepository;

    @Autowired
    private final IUserRepository userRepository;

    @Autowired
    private final ILivreRepository livreRepository;

    @Autowired
    private final IReservationRepository reservationRepository;

    @Override
    public Integer nbEmprunt() {
        return empruntRepository.findAll().size();
    }

    @Override
    public Map<Livre, Integer> nbLivreTopEmprunt() {
        List<Livre> tousLivres = livreRepository.findAll();

        Map<Livre, Integer> nbEmpruntsParLivre = new HashMap<>();

        for (Livre livre : tousLivres) {
            int nbEmprunts = empruntRepository.countByLivre(livre);
            nbEmpruntsParLivre.put(livre, nbEmprunts);
        }

        Map<Livre, Integer> resultat = nbEmpruntsParLivre.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        return resultat;
    }

    @Override
    public Integer nbEmpruntMois() {
        LocalDate debutMois = LocalDate.now().withDayOfMonth(1);
        Date dateDebutMois = Date.from(debutMois.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate finMois = debutMois.plusMonths(1).minusDays(1);
        Date dateFinMois = Date.from(finMois.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return empruntRepository.countByDateDebutBetween(dateDebutMois, dateFinMois);
    }

    @Override
    public Integer nbReservationMois() {
        LocalDate debutMois = LocalDate.now().withDayOfMonth(1);
        Date dateDebutMois = Date.from(debutMois.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate finMois = debutMois.plusMonths(1).minusDays(1);
        Date dateFinMois = Date.from(finMois.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return reservationRepository.countByDateDebutBetween(dateDebutMois, dateFinMois);
    }

    @Override
    public Integer nbReservationEnAttente() {
        return reservationRepository.countByPrise(false);
    }

    @Override
    public Integer nbLivreTotale() {
        return livreRepository.findAll().size();
    }

    @Override
    public Map<String, Map<String, Integer>> getBooksAndCopies() {
        List<Livre> tousLivres = livreRepository.findAll();
        Map<String, Map<String, Integer>> booksAndCopies = new HashMap<>();

        for (Livre livre : tousLivres) {
            int nbCopiesDisponibles = livre.getNbExemplaire() - livre.getNbExemplaireEmprinte();

            Map<String, Integer> exemplaireInfo = new HashMap<>();
            exemplaireInfo.put("nbExemplaireTotal", livre.getNbExemplaire());
            exemplaireInfo.put("nbCopiesDisponibles", nbCopiesDisponibles);

            booksAndCopies.put(livre.getTitre(), exemplaireInfo);
        }

        return booksAndCopies;
    }

    @Override
    public Map<Livre, List<Reservation>> getNbReservationEnAttenteParBook() {
        List<Reservation> reservationsEnAttente = reservationRepository.findByPriseFalse();
        Map<Livre, List<Reservation>> reservationsParLivre = reservationsEnAttente.stream()
                .collect(Collectors.groupingBy(Reservation::getLivre));

        Map<Livre, List<Reservation>> result = new HashMap<>();

        for (Map.Entry<Livre, List<Reservation>> entry : reservationsParLivre.entrySet()) {
            List<Reservation> reservationsTriees = entry.getValue().stream()
                    .sorted((r1, r2) -> r1.getDateReservation().compareTo(r2.getDateReservation()))
                    .collect(Collectors.toList());
            result.put(entry.getKey(), reservationsTriees);
        }
        return result;
    }
}
