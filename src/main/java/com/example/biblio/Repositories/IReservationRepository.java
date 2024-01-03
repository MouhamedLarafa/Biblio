package com.example.biblio.Repositories;

import com.example.biblio.entities.Emprunt;
import com.example.biblio.entities.Livre;
import com.example.biblio.entities.Reservation;
import com.example.biblio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findAllByLivre(Livre livre);

    Reservation findDistinctFirstByPriseIsFalseAndLivre(Livre livre);

    List<Reservation> findAllByLivreAndDateDebut(Livre l , Date d);

    int countByDateDebutBetween(Date dateDebut, Date dateFin);

    int countByPrise(boolean prise);

    List<Reservation> findByPriseFalse();

    List<Reservation> findAllByUser(User u);



}
