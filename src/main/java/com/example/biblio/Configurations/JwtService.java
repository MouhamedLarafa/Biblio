package com.example.biblio.Configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY="be6QByu50lw+PZ2DBfUsYAHJemInKxtUJUElz6Vaf/4667AmLwuO4eEEILBHp0Cg9VST5pPP2bTC8JcDedq+H13lbWSZ0jNUMb3hzVGkUqFn2s4xJ0lfpBznzv8Y61KLl9aqruCeKNnT8WHXK67Q3BfwRPHsgaGSU/oIOEm26nWYsPrqs9i2VJOrxrqEg/rIPGT1DmUsTN/qSWjOUhiOF8dWo0bt/fEDLU2Z/sdYRGRWdVKUBgw1FVHH3LO2UTyRipLohRU5mj5LoSU2cN5F8tAtertcNMI/vxGvDvzBpJqEfRNh+/Xve6e+FUaKi4Anovdrqt47UhNRPKPHK6Gb7bUSqMUOlKNGHbsZhED8p0Q=\n";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails){
          return Jwts
                  .builder()
                  .setClaims(extractClaims)
                  .setSubject(userDetails.getUsername())
                  .setIssuedAt(new Date(System.currentTimeMillis()))
                  .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                  .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                  .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
