package com.example.biblio.Controllers;

import com.example.biblio.Configurations.AuthenticationRequest;
import com.example.biblio.Configurations.AuthenticationResponse;
import com.example.biblio.Configurations.RegisterRequest;
import com.example.biblio.Services.IUserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentification/v1")
@RequiredArgsConstructor
public class AuthenticationControler {

    private final IUserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(userServices.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(userServices.authenticate(request));

    }
}
