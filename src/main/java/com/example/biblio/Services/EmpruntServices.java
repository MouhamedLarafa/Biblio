package com.example.biblio.Services;

import com.example.biblio.Repositories.IEmpruntRepository;
import com.example.biblio.Repositories.ILivreRepository;
import com.example.biblio.entities.Emprunt;
import com.example.biblio.entities.Livre;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmpruntServices implements IEmpruntServices{

    @Autowired
    private final IEmpruntRepository empruntRepository;

    @Autowired
    private final ILivreRepository livreRepository;

    @Override
    public ResponseEntity<?> addEmprunt(Emprunt emprunt, Livre livre) {
        if(livre.getNbCopie() - livre.getNbCopieEmprinte()==0){
            return new ResponseEntity<String>("Aucune copie disponible pour l'emprunt.", HttpStatus.BAD_REQUEST);
        }
        else {
            livre.setNbCopieEmprinte(livre.getNbCopieEmprinte()+1);
            livreRepository.save(livre);
            emprunt.setLivre(livre);
            empruntRepository.save(emprunt);
            return new ResponseEntity<String>("Emprunt ajouté avec succès.", HttpStatus.OK);
        }
    }
}
