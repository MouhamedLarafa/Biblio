package com.example.biblio.Services;

import com.example.biblio.Configurations.AuthenticationRequest;
import com.example.biblio.Configurations.AuthenticationResponse;
import com.example.biblio.Configurations.JwtService;
import com.example.biblio.Configurations.RegisterRequest;
import com.example.biblio.Repositories.IUserRepository;
import com.example.biblio.entities.Role;
import com.example.biblio.entities.User;
import com.itextpdf.io.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public ResponseEntity<?> register(RegisterRequest request) throws DocumentException, java.io.IOException {
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
            user = userRepository.save(user);
            generateCard(user);
            String rep = "Add succesfully.";
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
    }

    public void generateCard(User u) throws IOException, DocumentException, java.io.IOException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Ajout de l'image à droite
        if (u.getImage() != null) {
            Image image = Image.getInstance(u.getImage());
            image.scaleAbsolute(100, 100);
            image.setAlignment(Image.RIGHT | Image.TEXTWRAP);
            document.add(image);
        }

        Paragraph userInfo = new Paragraph();
        userInfo.add("Nom: " + u.getNom() + "\n");
        userInfo.add("Prénom: " + u.getPrenom() + "\n");
        userInfo.add("Date de Naissance: " + u.getDateNaissance() + "\n");
        userInfo.add("Numéro de Téléphone: " + u.getNumTel() + "\n");
        userInfo.add("Email: " + u.getEmail() + "\n");

        document.add(userInfo);

        document.close();

        byte[] pdfBytes = outputStream.toByteArray();

        u.setCard(pdfBytes);
        userRepository.save(u);
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

    @Override
    public void logOut() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    }
}
