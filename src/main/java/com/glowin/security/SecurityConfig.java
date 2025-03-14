package com.glowin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private TokenFilter tokenFilter;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private IpAddressFilter ipAddressFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui.html", "/v3/api-docs", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/imagenes-servicios/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categorias-servicios/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/servicios/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reservas/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reservas/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/reservas/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/reservas/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/reservas/**").authenticated()
                        .requestMatchers("/servicios/**", "/empleados/**", "/categorias-servicios/**")
                        .hasAnyAuthority("SUPER_ADMINISTRADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/usuarios/**")
                        .hasAnyAuthority("SUPER_ADMINISTRADOR", "ADMINISTRADOR", "CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/**")
                        .hasAnyAuthority("SUPER_ADMINISTRADOR", "ADMINISTRADOR", "CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**")
                        .hasAnyAuthority("SUPER_ADMINISTRADOR", "ADMINISTRADOR", "CLIENTE")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}