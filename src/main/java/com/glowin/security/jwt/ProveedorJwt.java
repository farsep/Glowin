package com.glowin.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class ProveedorJwt {

    @Value("${jwt.secreto}") // Clave secreta para firmar el token (se configura en application.properties)
    private String jwtSecreto;

    @Value("${jwt.expiracion}") // Tiempo de expiración del token en milisegundos
    private int jwtExpiracion;

    // Genera un token JWT
    public String generarToken(UserDetails detallesUsuario) {
        Map<String, Object> claims = new HashMap<>();
        return crearToken(claims, detallesUsuario.getUsername());
    }

    // Crea el token JWT
    private String crearToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiracion))
                .signWith(obtenerClaveFirma(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Obtiene la clave de firma
    private Key obtenerClaveFirma() {
        return Keys.hmacShaKeyFor(jwtSecreto.getBytes());
    }

    // Valida el token JWT
    public boolean validarToken(String token, UserDetails detallesUsuario) {
        final String nombreUsuario = extraerNombreUsuario(token);
        return (nombreUsuario.equals(detallesUsuario.getUsername()) && !esTokenExpirado(token));
    }

    // Extrae el nombre de usuario del token
    public String extraerNombreUsuario(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    // Extrae la fecha de expiración del token
    public Date extraerExpiracion(String token) {
        return extraerClaim(token, Claims::getExpiration);
    }

    // Extrae un claim específico del token
    private <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraerTodosLosClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extrae todos los claims del token
    private Claims extraerTodosLosClaims(String token) {
        return Jwts.parser()
                .setSigningKey(obtenerClaveFirma())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Verifica si el token ha expirado
    private boolean esTokenExpirado(String token) {
        return extraerExpiracion(token).before(new Date());
    }
}