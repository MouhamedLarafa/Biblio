package com.example.biblio.Controllers;

import com.example.biblio.Services.IEmpruntServices;
import com.example.biblio.entities.Emprunt;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprunt")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmpruntController {
    private final IEmpruntServices empruntServices;

    @GetMapping("/empruntsbyuser/{id}")
    public List<Emprunt> getAllByUser(@PathVariable("id") Integer id){
        return empruntServices.getAllByUser(id);
    }

}
