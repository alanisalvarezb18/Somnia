package com.somnia.somnia.service;

import com.somnia.somnia.model.User;
import com.somnia.somnia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Optional<User> optional = this.repository.findByCorreo(correo);

        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("El usuario no existe");
        }

        User usuario = optional.get();
        return org.springframework.security.core.userdetails.User.withUsername(usuario.getCorreo()).password(usuario.getContrasena()).roles(usuario.getRol()).build();
    }
}