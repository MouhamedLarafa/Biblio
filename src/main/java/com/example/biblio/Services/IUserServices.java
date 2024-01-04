package com.example.biblio.Services;

import com.example.biblio.Configurations.AuthenticationRequest;
import com.example.biblio.Configurations.AuthenticationResponse;
import com.example.biblio.Configurations.RegisterRequest;
import com.example.biblio.entities.User;
import com.itextpdf.text.DocumentException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface IUserServices {
    public ResponseEntity<?> register(RegisterRequest request) throws DocumentException, IOException;
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public User getConnectedUser();
    public List<User> getAllUser();
    public void logOut();
}
