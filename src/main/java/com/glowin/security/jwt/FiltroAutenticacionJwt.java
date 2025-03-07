package com.glowin.security.jwt;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FiltroAutenticacionJwt extends OncePerRequestFilter {

    private final ProveedorJwt proveedorJwt;
    private final UserDetailsService servicioDetallesUsuario;

    public FiltroAutenticacionJwt(ProveedorJwt proveedorJwt, @Qualifier("customUserDetailsService") UserDetailsService servicioDetallesUsuario) {
        this.proveedorJwt = proveedorJwt;
        this.servicioDetallesUsuario = servicioDetallesUsuario;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest solicitud, HttpServletResponse respuesta, FilterChain cadenaFiltros)
            throws ServletException, IOException {
        final String cabeceraAutorizacion = solicitud.getHeader("Authorization");
        final String jwt;
        final String nombreUsuario;

        if (cabeceraAutorizacion == null || !cabeceraAutorizacion.startsWith("Bearer ")) {
            cadenaFiltros.doFilter(solicitud, respuesta);
            return;
        }

        jwt = cabeceraAutorizacion.substring(7);
        nombreUsuario = proveedorJwt.extraerNombreUsuario(jwt);

        if (nombreUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails detallesUsuario = this.servicioDetallesUsuario.loadUserByUsername(nombreUsuario);
            if (proveedorJwt.validarToken(jwt, detallesUsuario)) {
                UsernamePasswordAuthenticationToken tokenAutenticacion = new UsernamePasswordAuthenticationToken(
                        detallesUsuario, null, detallesUsuario.getAuthorities());
                tokenAutenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(solicitud));
                SecurityContextHolder.getContext().setAuthentication(tokenAutenticacion);
            }
        }

        cadenaFiltros.doFilter(solicitud, respuesta);
    }
}