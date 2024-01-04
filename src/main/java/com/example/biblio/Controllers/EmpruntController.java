package com.example.biblio.Controllers;

import com.example.biblio.Repositories.IEmpruntRepository;
import com.example.biblio.Repositories.IUserRepository;
import com.example.biblio.Services.IEmpruntServices;
import com.example.biblio.entities.Emprunt;
import com.example.biblio.entities.Reservation;
import com.example.biblio.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprunt")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmpruntController {
    private final IEmpruntServices empruntServices;
    private final IUserRepository userRepository;
    private final IEmpruntRepository empruntRepository;


    @GetMapping("/empruntsbyuser/{id}")
    public List<Emprunt> getAllByUser(@PathVariable("id") Integer id){
        return empruntServices.getAllByUser(id);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addEmprint(@RequestBody Emprunt emprunt,
                                            @PathVariable("id") Integer idLivre) {
        return empruntServices.addEmprunt(emprunt, idLivre);
    }

    @GetMapping("/empruntsuser")
    public List<Emprunt> getEmpruntsUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return empruntServices.getAllByUser(u.getIdUser());
    }

    @GetMapping("/all")
    public List<Emprunt> retrieveAll() {
        return empruntServices.retrieveAll();
    }

    @PutMapping("/edit")
    public void editEmp(@RequestBody Emprunt emprunt){
        empruntRepository.save(emprunt);
    }


}
