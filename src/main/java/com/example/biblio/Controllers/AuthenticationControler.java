package com.example.biblio.Controllers;

import com.example.biblio.Configurations.AuthenticationRequest;
import com.example.biblio.Configurations.AuthenticationResponse;
import com.example.biblio.Configurations.RegisterRequest;
import com.example.biblio.Services.IUserServices;
import com.example.biblio.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/authentification/v1")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationControler {

    private final IUserServices userServices;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(@RequestParam("register") String requestJson, @RequestParam(value = "file") @Nullable MultipartFile file) throws IOException {
        ObjectMapper objectMapper =new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        RegisterRequest req = objectMapper.readValue(requestJson, RegisterRequest.class);
        if(file!=null){
            byte[] bytes = file.getBytes();
            req.setImage(bytes);
        }

        return userServices.register(req);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(userServices.authenticate(request));
    }

    @GetMapping("/getuser")
    public User getConnectedUser(){
        return userServices.getConnectedUser();
    }

    @GetMapping("/Allusers")
    public List<User> getAllUsers(){
        return userServices.getAllUser();
    }
}
