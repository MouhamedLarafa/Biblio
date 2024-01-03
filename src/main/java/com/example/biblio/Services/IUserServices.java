package com.example.biblio.Services;

import com.example.biblio.Configurations.AuthenticationRequest;
import com.example.biblio.Configurations.AuthenticationResponse;
import com.example.biblio.Configurations.RegisterRequest;
import com.example.biblio.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserServices {
    public ResponseEntity<?> register(RegisterRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public User getConnectedUser();
    public List<User> getAllUser();
}
