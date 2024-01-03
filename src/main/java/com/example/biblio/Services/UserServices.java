package com.example.biblio.Services;

import com.example.biblio.Configurations.AuthenticationRequest;
import com.example.biblio.Configurations.AuthenticationResponse;
import com.example.biblio.Configurations.JwtService;
import com.example.biblio.Configurations.RegisterRequest;
import com.example.biblio.Repositories.IUserRepository;
import com.example.biblio.entities.Role;
import com.example.biblio.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServices implements IUserServices {

    @Autowired
    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        var user = User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .numTel(request.getNumTel())
                .adresse(request.getAdresse())
                .dateNaissance(request.getDateNaissance())
                .image(request.getImage())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User u = userRepository.findByEmail(request.getEmail()).orElse(null);
        Map<String, String> responseMap = new HashMap<>();

        if(u!=null){
            String rep = "Email already exist.";
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
        else {
            userRepository.save(user);
            String rep = "Add succesfully.";
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        System.out.println(user.getIdUser());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public User getConnectedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return u;
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
