package com.example.biblio.Services;

import com.example.biblio.Repositories.ICategorieRepository;
import com.example.biblio.entities.Categorie;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class CategorieServices implements ICategorieServices  {
    @Autowired
    private final ICategorieRepository categorieRepository;

    @Override
    public Categorie addOrUpdateCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public void deleteCategorie(Integer idCategorie) {
        categorieRepository.deleteById(idCategorie);
    }

    @Override
    public Categorie retrieveCategorie(Integer idCategorie) {
       return categorieRepository.findById(idCategorie).orElse(new Categorie());
    }

    @Override
    public List<Categorie> retrieveAllCategories() {
         List< Categorie > categories = new ArrayList<>();
        categorieRepository.findAll().forEach(categories::add);
        return categories;
    }
}
