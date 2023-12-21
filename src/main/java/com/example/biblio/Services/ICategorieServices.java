package com.example.biblio.Services;

import com.example.biblio.entities.Categorie;

import java.util.List;

public interface ICategorieServices {
    public Categorie addOrUpdateCategorie(Categorie categorie);
    public void deleteCategorie(Integer idCategorie);
    public Categorie retrieveCategorie(Integer idCategorie);
    public List<Categorie> retrieveAllCategories();
}
