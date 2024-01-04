package com.example.biblio.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Integer idUser;
    String nom;
    String prenom;
    @Enumerated(EnumType.STRING)
    Role role;
    @Temporal(TemporalType.DATE)
    Date dateNaissance;
    String adresse;
    String email;
    String password;
    String numTel;
    byte[] image;
    byte[] card;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    Set<Emprunt> emprunts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    Set<Reservation> reservations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       // return List.of(new SimpleGrantedAuthority(role.name()));
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
