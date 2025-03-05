package com.glowin.security.jwt;

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

    public FiltroAutenticacionJwt(ProveedorJwt proveedorJwt, UserDetailsService servicioDetallesUsuario) {
        this.proveedorJwt = proveedorJwt;
        this.servicioDetallesUsuario = servicioDetallesUsuario;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest solicitud, HttpServletResponse respuesta, FilterChain cadenaFiltros)
            throws ServletException, IOException {
        final String cabeceraAutorizacion = solicitud.getHeader("Authorization");
        final String jwt;
        final String nombreUsuario;

        // Verificar si el encabezado Authorization está presente y comienza con "Bearer "
        if (cabeceraAutorizacion == null || !cabeceraAutorizacion.startsWith("Bearer ")) {
            cadenaFiltros.doFilter(solicitud, respuesta);
            return;
        }

        // Extraer el token JWT (eliminando "Bearer " del encabezado)
        jwt = cabeceraAutorizacion.substring(7);
        nombreUsuario = proveedorJwt.extraerNombreUsuario(jwt);

        // Validar el token y establecer la autenticación en el contexto de seguridad
        if (nombreUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails detallesUsuario = this.servicioDetallesUsuario.loadUserByUsername(nombreUsuario);
            if (proveedorJwt.validarToken(jwt, detallesUsuario)) {
                UsernamePasswordAuthenticationToken tokenAutenticacion = new UsernamePasswordAuthenticationToken(
                        detallesUsuario, null, detallesUsuario.getAuthorities());
                tokenAutenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(solicitud));
                SecurityContextHolder.getContext().setAuthentication(tokenAutenticacion);
            }
        }

        // Continuar con la cadena de filtros
        cadenaFiltros.doFilter(solicitud, respuesta);
    }
}