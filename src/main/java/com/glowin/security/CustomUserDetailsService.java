package com.glowin.security;

import com.glowin.models.Usuario;
import com.glowin.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private IUsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//         Implement your logic to load user details from the database or any other source
//         For example:
         Usuario user = userRepository.findByEmail(email);
         if (user == null) {
             throw new UsernameNotFoundException("User not found");
         }
         return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}