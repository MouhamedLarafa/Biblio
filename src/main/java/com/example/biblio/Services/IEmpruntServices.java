package com.example.biblio.Services;

import com.example.biblio.entities.Emprunt;
import com.example.biblio.entities.Livre;
import org.springframework.http.ResponseEntity;

public interface IEmpruntServices {
    public ResponseEntity<?> addEmprunt(Emprunt emprunt, Livre livre);
}
