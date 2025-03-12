package com.glowin.security;

import com.glowin.models.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt_secreto}")
    private String API_secret;

    //Agregando duracion del token
    @Value("${jwt_expiracion}")
    private Long duracion;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(API_secret.getBytes());
    }

    public String generarToken(Usuario user) {
        try {
            return Jwts.builder()
                    .setIssuer("Glowin")
                    .setSubject(user.getUsername())
                    .claim("id", user.getId())
                    .setExpiration(Date.from(Instant.now().plusMillis(duracion)))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception exception) {
            throw new RuntimeException("Error creating token", exception);
        }
    }

    private Instant getExpirationDate(int days) {
        return LocalDate.now().plusDays(days).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
    }

    public String getSubjectAndVerify(String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Token is empty");
        }
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Error verifying token", e);
        }
    }
}