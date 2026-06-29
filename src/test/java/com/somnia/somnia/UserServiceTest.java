package com.somnia.somnia;

import com.somnia.somnia.model.User;
import com.somnia.somnia.model.UserResponseDTO;
import com.somnia.somnia.repository.UserRepository;
import com.somnia.somnia.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    @Test
    void saveUsuarioExitoso() {

        User usuario = new User();

        usuario.setNombre("Alanis");
        usuario.setCorreo("alanis@test.com");
        usuario.setContrasena("12345678");

        when(repository.findByCorreo("alanis@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("12345678")).thenReturn("password-encriptado");
        when(repository.save(any(User.class))).thenAnswer(i -> {
            User user = i.getArgument(0);
            user.setId(1);
            return user;
        });

        UserResponseDTO response = service.saveUsuario(usuario);
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Alanis", response.getNombre());
        assertEquals("ESTUDIANTE", response.getRol());
        verify(passwordEncoder).encode("12345678");
        verify(repository).save(any(User.class));
    }

    @Test
    void loginExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Valeria");
        usuario.setCorreo("valeria@test.com");
        usuario.setContrasena("password-encriptado");
        usuario.setRol("ESTUDIANTE");

        when(repository.findByCorreo("valeria@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("12345678", "password-encriptado")).thenReturn(true);
        UserResponseDTO response = service.login("valeria@test.com", "12345678");
        assertNotNull(response);
        assertEquals("valeria@test.com", response.getCorreo());
        assertEquals("Valeria", response.getNombre());
    }

    @Test
    void loginCredencialesIncorrectas() {
        User usuario = new User();
        usuario.setCorreo("laura@test.com");
        usuario.setContrasena("password-encriptado");
        when(repository.findByCorreo("laura@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("incorrecta", "password-encriptado")).thenReturn(false);
        assertThrows(RuntimeException.class, () -> service.login("laura@test.com", "incorrecta"));
    }
}