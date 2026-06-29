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
import java.util.List;
import com.somnia.somnia.repository.SleepGoalRepository;
import com.somnia.somnia.repository.SleepRecordRepository;
import com.somnia.somnia.model.SleepGoal;
import com.somnia.somnia.model.SleepRecord;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SleepRecordRepository sleepRecordRepository;

    @Mock
    private SleepGoalRepository sleepGoalRepository;

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
    void saveUsuarioCorreoExistente() {
        User usuario = new User();
        usuario.setNombre("Alanis");
        usuario.setCorreo("alanis@test.com");
        usuario.setContrasena("12345678");

        when(repository.findByCorreo("alanis@test.com")).thenReturn(Optional.of(usuario));
        assertThrows(RuntimeException.class, () -> service.saveUsuario(usuario));
        verify(repository, never()).save(any(User.class));
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

    @Test
    void loginUsuarioNoExiste() {
        when(repository.findByCorreo("noexiste@test.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.login("noexiste@test.com", "12345678"));
    }

    @Test
    void findAllExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Laura");
        usuario.setCorreo("laura@test.com");
        usuario.setRol("ESTUDIANTE");
        when(repository.findAll()).thenReturn(List.of(usuario));

        List<UserResponseDTO> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Laura", response.get(0).getNombre());
    }

    @Test
    void findByIdExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Aaron");
        usuario.setCorreo("aaron@test.com");
        usuario.setRol("ADMIN");

        when(repository.findById(1)).thenReturn(Optional.of(usuario));
        UserResponseDTO response = service.findById(1);
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Aaron", response.getNombre());
    }

    @Test
    void findByIdNoExiste() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(1));
    }

    @Test
    void findByCorreoExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Valeria");
        usuario.setCorreo("valeria@test.com");
        usuario.setRol("ESTUDIANTE");

        when(repository.findByCorreo("valeria@test.com")).thenReturn(Optional.of(usuario));
        UserResponseDTO response = service.findByCorreo("valeria@test.com");
        assertNotNull(response);
        assertEquals("valeria@test.com", response.getCorreo());
    }

    @Test
    void findByCorreoNoExiste() {
        when(repository.findByCorreo("noexiste@test.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findByCorreo("noexiste@test.com"));
    }

    @Test
    void editUsuarioExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Valeria");
        usuario.setCorreo("valeria@test.com");
        usuario.setContrasena("password-viejo");
        usuario.setRol("ESTUDIANTE");

        User usuarioEdit = new User();
        usuarioEdit.setNombre("Valeria Editada");
        usuarioEdit.setCorreo("valeria.editada@test.com");
        usuarioEdit.setContrasena("12345678");
        usuarioEdit.setRol("ESTUDIANTE");

        when(repository.findById(1)).thenReturn(Optional.of(usuario));
        when(repository.findByCorreo("valeria.editada@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("12345678")).thenReturn("password-nuevo");
        when(repository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        UserResponseDTO response = service.editUsuario(1, usuarioEdit);
        assertNotNull(response);
        assertEquals("Valeria Editada", response.getNombre());
        assertEquals("valeria.editada@test.com", response.getCorreo());
    }

    @Test
    void editUsuarioNoExiste() {
        User usuarioEdit = new User();
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.editUsuario(1, usuarioEdit));
    }

    @Test
    void deleteUsuarioExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Laura");
        usuario.setCorreo("laura@test.com");
        usuario.setRol("ESTUDIANTE");

        SleepRecord registro1 = new SleepRecord();
        registro1.setId(1);

        SleepRecord registro2 = new SleepRecord();
        registro2.setId(2);

        SleepGoal objetivo = new SleepGoal();
        objetivo.setId(1);
        objetivo.setUsuario(usuario);

        List<SleepRecord> registros = List.of(registro1, registro2);

        when(repository.findById(1)).thenReturn(Optional.of(usuario));
        when(sleepRecordRepository.findByUsuarioId(1)).thenReturn(registros);
        when(sleepGoalRepository.findByUsuarioId(1)).thenReturn(Optional.of(objetivo));
        service.deleteUsuario(1);
        verify(sleepRecordRepository).deleteAll(registros);
        verify(sleepGoalRepository).delete(objetivo);
        verify(repository).deleteById(1);
    }

    @Test
    void deleteUsuarioSinRegistrosNiObjetivo() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Aaron");
        usuario.setCorreo("aaron@test.com");
        usuario.setRol("ESTUDIANTE");

        when(repository.findById(1)).thenReturn(Optional.of(usuario));
        when(sleepRecordRepository.findByUsuarioId(1)).thenReturn(List.of());
        when(sleepGoalRepository.findByUsuarioId(1)).thenReturn(Optional.empty());
        service.deleteUsuario(1);
        verify(sleepRecordRepository, never()).deleteAll(any());
        verify(sleepGoalRepository, never()).delete(any());
        verify(repository).deleteById(1);
    }

    @Test
    void deleteUsuarioNoExiste() {

        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.deleteUsuario(1));
        verify(sleepRecordRepository, never()).findByUsuarioId(any());
        verify(sleepGoalRepository, never()).findByUsuarioId(any());
        verify(repository, never()).deleteById(any());
    }
}