package com.example.biblio.Controllers;

import com.example.biblio.Configurations.RegisterRequest;
import com.example.biblio.Services.ILivreServices;
import com.example.biblio.entities.Livre;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin("*")
@RequiredArgsConstructor
public class LivreControllers {
    @Autowired
    private final ILivreServices livreService;

    @PostMapping(value = "/addOrUpdate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Livre> addOrUpdateLivre(@RequestParam("livre") String livreJson,
                                                  @RequestParam("idCategorie") @Nullable Integer idCategorie ,
                                                  @RequestParam(value = "file") @Nullable MultipartFile file)
            throws IOException, MessagingException, jakarta.mail.MessagingException {
        ObjectMapper objectMapper =new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Livre l = objectMapper.readValue(livreJson, Livre.class);
        l.setNbExemplaireEmprinte(0);
        if(file!=null){
            byte[] bytes = file.getBytes();
            l.setImage(bytes);
        }
        Livre savedLivre;
        if(l.getIdLivre()==null){
            savedLivre = livreService.addOrUpdateLivre(l, idCategorie);
        }
        else {
            System.out.println(l.getCategorie().getIdCategorie());
            savedLivre = livreService.addOrUpdateLivre(l, l.getCategorie().getIdCategorie());
        }
        return new ResponseEntity<>(savedLivre, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{idLivre}")
    public ResponseEntity<Void> deleteLivre(@PathVariable Integer idLivre) {
        livreService.deleteLivre(idLivre);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/retrieve/{idLivre}")
    public ResponseEntity<Livre> retrieveLivre(@PathVariable Integer idLivre) {
        Livre livre = livreService.retrieveLivre(idLivre);
        return new ResponseEntity<>(livre, HttpStatus.OK);
    }

    @GetMapping("/retrieveAll")
    public ResponseEntity<List<Livre>> retrieveAllLivres() {
        List<Livre> livres = livreService.retrieveAllLivres();
        return new ResponseEntity<>(livres, HttpStatus.OK);
    }

    @GetMapping("/retrieveByCategorie/{idCategorie}")
    public ResponseEntity<List<Livre>> retrieveAllLivresByCategorie(@PathVariable Integer idCategorie) {
        List<Livre> livres = livreService.retrieveAllLivresByCategorie(idCategorie);
        return new ResponseEntity<>(livres, HttpStatus.OK);
    }

    @GetMapping("/retrieveAllowedBooks")
    public ResponseEntity<List<Livre>> retrieveAllAllowedBooks() {
        List<Livre> livres = livreService.retrieveAllAllowedBooks();
        return new ResponseEntity<>(livres, HttpStatus.OK);
    }
}
