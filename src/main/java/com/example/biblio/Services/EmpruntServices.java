package com.example.biblio.Services;

import com.example.biblio.Configurations.Mail;
import com.example.biblio.Repositories.*;
import com.example.biblio.Repositories.ILivreRepository;
import com.example.biblio.entities.Emprunt;
import com.example.biblio.entities.Livre;
import com.example.biblio.entities.Reservation;
import com.example.biblio.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Service
@AllArgsConstructor
public class EmpruntServices implements IEmpruntServices{

    @Autowired
    private final IEmpruntRepository empruntRepository;

    @Autowired
    private final ILivreRepository livreRepository;

    IEmailServices emailServices;

    @Autowired
    private final IReservationRepository reservationRepository;

    @Autowired
    private final IUserRepository userRepository;

    @Override
    public ResponseEntity<?> addEmprunt(Emprunt emprunt, Livre livre) {
        if(livre.getNbExemplaire() - livre.getNbExemplaireEmprinte()<=0){
            return new ResponseEntity<String>("Aucune copie disponible pour l'emprunt. Vous pouvez ajouter une réservation pour ce livre", HttpStatus.BAD_REQUEST);
        }
        else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                User user = userRepository.findByEmail(username).orElseThrow();
                emprunt.setUser(user);

                livre.setNbExemplaireEmprinte(livre.getNbExemplaireEmprinte() + 1);
                livreRepository.save(livre);
                emprunt.setLivre(livre);
                empruntRepository.save(emprunt);
                return new ResponseEntity<String>("Emprunt ajouté avec succès.", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<String>("Utilisateur non authentifié.", HttpStatus.UNAUTHORIZED);
            }

        }
    }


    @Override
    public ResponseEntity<?> retourLivre(Emprunt emprunt) throws MessagingException, jakarta.mail.MessagingException {
        emprunt.setEtat(true);
        empruntRepository.save(emprunt);
        Livre l = emprunt.getLivre();
        l.setNbExemplaireEmprinte(l.getNbExemplaireEmprinte()-1);
        livreRepository.save(l);


        Reservation reservation = reservationRepository.findDistinctFirstByPriseIsFalseAndLivre(l);
        Mail mail = new Mail();
        mail.setFrom("larafa755@gmail.com");
        mail.setSubject("Rappel de remise du livre");
        mail.setMailTo(reservation.getUser().getEmail());
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("titreLivre", l.getTitre());
        model.put("anneePublication", l.getDatePublication());
        model.put("genre", l.getCategorie().getNomCategorie());
        model.put("auteur", l.getNomAuteur());
        mail.setProps(model);
        emailServices.sendEmail(mail,"EmailReservationDisponible.html");

        return new ResponseEntity<String>("Emprunt retourné avec succès.", HttpStatus.OK);
    }

    @Scheduled(cron = "0 12 * * * *")
    public void rappels() throws MessagingException, jakarta.mail.MessagingException {

        List<Emprunt> emprunts = new ArrayList<>();
        empruntRepository.findAll().forEach(emprunts::add);

        for (Emprunt e : emprunts){
            if (e.getDateFin().before(new Date(System.currentTimeMillis())) && !e.isEtat()) {
                Mail mail = new Mail();

                mail.setFrom("larafa755@gmail.com");
                mail.setMailTo(e.getUser().getEmail());
                mail.setSubject("Rappel de remise du livre");

                Map<String, Object> model = new HashMap<String, Object>();
                User u = e.getUser();
                model.put("namedemandeur", u.getNom() + " " + u.getPrenom());
                model.put("datedebut", e.getDateDebut());
                model.put("datefin", e.getDateFin());
                mail.setProps(model);
                //emailServices.sendEmail(mail,"EmailRappelEmprunt.html");
            }
        }

    }

}
