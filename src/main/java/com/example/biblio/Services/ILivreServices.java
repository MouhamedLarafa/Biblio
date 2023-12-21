package com.example.biblio.Services;

import com.example.biblio.entities.Livre;

import java.util.List;

public interface ILivreServices {
    public Livre addOrUpdateLivre(Livre livre, Integer idCategorie);
    public void deleteLivre(Integer idLivre);
    public Livre retrieveLivre(Integer idLivre);
    public List<Livre> retrieveAllLivres();
    public List<Livre> retrieveAllLivresByCategorie(Integer idCategorie);
}
