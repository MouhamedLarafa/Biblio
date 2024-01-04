package com.example.biblio.Controllers;
import com.example.biblio.Configurations.Mail;
import com.example.biblio.Services.EmailServices;
import com.example.biblio.Services.ILivreServices;
import com.example.biblio.entities.Livre;
import com.example.biblio.entities.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/email")
public class test {

    @Autowired
    private EmailServices emailService;

    @Autowired
    private ILivreServices livreServices;

    @GetMapping("/sendTestEmail")
    public String sendTestEmail() {
        try {
            Mail mail = new Mail();

            mail.setFrom("larafamouhamed5@gmail.com");
            mail.setMailTo("fouedtellili19@gmail.com");
            mail.setSubject("Rappel de remise du livre");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("namedemandeur", "u.getNom() +  + u.getPrenom()");
            model.put("datedebut", "e.getDateDebut()");
            model.put("datefin", "e.getDateFin()");
            mail.setProps(model);
            emailService.sendEmail(mail, "EmailRappelEmprunt.html");

            return "Test email sent successfully!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send test email.";
        }



    }

    @GetMapping("/books")
    public List<Livre> retrieveAllLivres() {
        return livreServices.retrieveAllLivres();
    }


}

