package com.example.biblio.Repositories;

import com.example.biblio.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategorieRepository extends JpaRepository<Categorie, Integer> {
}
