package com.glowin.security;

import com.glowin.repository.IUsuarioRepository;
import com.glowin.models.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Configuration
public class PreFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUsuarioRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "");
            try {
                String userName = tokenService.getSubjectAndVerify(token);
                if (userName != null) {
                    Usuario user = (Usuario) userRepo.findByEmail(userName);
                    if (user != null) {
                        var authorities = user.getAuthorities().stream()
                                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                                .collect(Collectors.toList());
                        var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                // Log the exception and continue the filter chain
                logger.error("Authentication error: ", e);
            }
        }
        filterChain.doFilter(request, response);
    }
}