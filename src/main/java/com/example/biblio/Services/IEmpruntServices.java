package com.example.biblio.Services;

import com.example.biblio.entities.Emprunt;
import com.example.biblio.entities.Livre;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.util.List;

public interface IEmpruntServices {
    public ResponseEntity<?> addEmprunt(Emprunt emprunt, Livre livre);
    public ResponseEntity<?> retourLivre(Emprunt emprunt) throws MessagingException, jakarta.mail.MessagingException;
    public List<Emprunt> getAllByUser(int idUser);

}
