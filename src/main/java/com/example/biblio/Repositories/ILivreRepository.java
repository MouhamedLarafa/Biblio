package com.example.biblio.Repositories;

import com.example.biblio.entities.Categorie;
import com.example.biblio.entities.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILivreRepository extends JpaRepository<Livre, Integer> {

    List<Livre> findAllByCategorie(Categorie categorie);

}
