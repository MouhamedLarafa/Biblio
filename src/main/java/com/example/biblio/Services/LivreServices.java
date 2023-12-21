package com.example.biblio.Services;

import com.example.biblio.Repositories.ICategorieRepository;
import com.example.biblio.Repositories.ILivreRepository;
import com.example.biblio.entities.Categorie;
import com.example.biblio.entities.Livre;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LivreServices implements ILivreServices{

    @Autowired
    private final ILivreRepository livreRepository;

    @Autowired
    private  final ICategorieRepository categorieRepository;

    @Override
    public Livre addOrUpdateLivre(Livre livre, Integer idCategorie) {
        Categorie categorie = categorieRepository.findById(idCategorie).orElse(null);
        if(categorie!=null){
            livre.setCategorie(categorie);
        }
        return livreRepository.save(livre);
    }

    @Override
    public void deleteLivre(Integer idLivre) {
        livreRepository.deleteById(idLivre);
    }

    @Override
    public Livre retrieveLivre(Integer idLivre) {
        return livreRepository.findById(idLivre).orElse(new Livre());
    }

    @Override
    public List<Livre> retrieveAllLivres() {
        List<Livre> livres = new ArrayList<>();
        livreRepository.findAll().forEach(livres::add);
        return livres;
    }

    @Override
    public List<Livre> retrieveAllLivresByCategorie(Integer idCategorie) {
        Categorie categorie = categorieRepository.findById(idCategorie).orElse(null);
        if(categorie!=null){
            return livreRepository.findAllByCategorie(categorie);
        }
        return null;
    }
}
