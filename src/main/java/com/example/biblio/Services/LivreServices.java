package com.example.biblio.Services;

import com.example.biblio.Configurations.Mail;
import com.example.biblio.Repositories.ICategorieRepository;
import com.example.biblio.Repositories.ILivreRepository;
import com.example.biblio.Repositories.IUserRepository;
import com.example.biblio.entities.Categorie;
import com.example.biblio.entities.Livre;
import com.example.biblio.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class LivreServices implements ILivreServices{

    @Autowired
    private final ILivreRepository livreRepository;

    @Autowired
    private final ICategorieRepository categorieRepository;

    @Autowired
    private final IUserRepository userRepository;

    IEmailServices emailServices;

    @Override
    public Livre addOrUpdateLivre(Livre livre, Integer idCategorie) throws MessagingException, jakarta.mail.MessagingException {
        Categorie categorie = categorieRepository.findById(idCategorie).orElse(null);
        if(categorie!=null){
            livre.setCategorie(categorie);
        }

        Mail mail = new Mail();
        mail.setFrom("larafa755@gmail.com");
        mail.setSubject("Rappel de remise du livre");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("titreLivre", livre.getTitre());
        model.put("anneePublication", livre.getDatePublication());
        model.put("genre", livre.getCategorie().getNomCategorie());
        model.put("auteur", livre.getNomAuteur());
        mail.setProps(model);


        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        if(livre.getIdLivre()!=null){
            for (User u : users){
                if (u.getEmail()!=null){
                    mail.setMailTo(u.getEmail());
                    emailServices.sendEmail(mail,"EmailLivreAjouter.html");
                }
            }
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

    @Override
    public List<Livre> retrieveAllAllowedBooks() {
        List<Livre> livres = retrieveAllLivres();
        List<Livre> disponible = new ArrayList<>();
        for (Livre l : livres){
            if (l.getNbExemplaire()-l.getNbExemplaireEmprinte()>0){
                disponible.add(l);
            }
        }
        return disponible;
    }

}
