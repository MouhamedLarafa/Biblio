package com.example.biblio.Services;

import com.example.biblio.entities.Livre;

import javax.mail.MessagingException;
import java.util.List;

public interface ILivreServices {
    public Livre addOrUpdateLivre(Livre livre, Integer idCategorie) throws MessagingException, jakarta.mail.MessagingException;
    public void deleteLivre(Integer idLivre);
    public Livre retrieveLivre(Integer idLivre);
    public List<Livre> retrieveAllLivres();
    public List<Livre> retrieveAllLivresByCategorie(Integer idCategorie);
    public List<Livre> retrieveAllAllowedBooks();
}
