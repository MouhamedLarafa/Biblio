package com.example.biblio.Services;

import com.example.biblio.Configurations.AuthenticationRequest;
import com.example.biblio.Configurations.AuthenticationResponse;
import com.example.biblio.Configurations.RegisterRequest;

public interface IUserServices {
    public AuthenticationResponse register(RegisterRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
}
