package com.example.biblio.Repositories;

import com.example.biblio.entities.Emprunt;
import com.example.biblio.entities.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IEmpruntRepository extends JpaRepository<Emprunt, Integer> {
    List<Emprunt> findAllByLivreAndEtatIsFalse(Livre livre);

    int countByLivre(Livre livre);

    int countByDateDebutBetween(Date dateDebut, Date dateFin);


}
