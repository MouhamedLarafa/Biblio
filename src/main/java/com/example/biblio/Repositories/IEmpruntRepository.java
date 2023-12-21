package com.example.biblio.Repositories;

import com.example.biblio.entities.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpruntRepository extends JpaRepository<Emprunt, Integer> {
}
