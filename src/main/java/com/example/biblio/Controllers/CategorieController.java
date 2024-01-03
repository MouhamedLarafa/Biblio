package com.example.biblio.Controllers;

import com.example.biblio.Services.ICategorieServices;
import com.example.biblio.entities.Categorie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategorieController {

    private final ICategorieServices categorieServices;

    @PostMapping("/addOrUpdate")
    public ResponseEntity<Categorie> addOrUpdateCategorie(@RequestBody Categorie categorie) {
        Categorie savedCategorie = categorieServices.addOrUpdateCategorie(categorie);
        return new ResponseEntity<>(savedCategorie, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idCategorie}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Integer idCategorie) {
        categorieServices.deleteCategorie(idCategorie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/retrieve/{idCategorie}")
    public ResponseEntity<Categorie> retrieveCategorie(@PathVariable Integer idCategorie) {
        Categorie categorie = categorieServices.retrieveCategorie(idCategorie);
        return new ResponseEntity<>(categorie, HttpStatus.OK);
    }

    @GetMapping("/retrieveAll")
    public List<Categorie> retrieveAllCategories() {
        return categorieServices.retrieveAllCategories();
    }
}

