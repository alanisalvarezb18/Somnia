package com.somnia.somnia;

import com.somnia.somnia.model.SleepGoal;
import com.somnia.somnia.model.SleepGoalRequestDTO;
import com.somnia.somnia.model.SleepGoalResponseDTO;
import com.somnia.somnia.model.User;
import com.somnia.somnia.repository.SleepGoalRepository;
import com.somnia.somnia.repository.UserRepository;
import com.somnia.somnia.service.SleepGoalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SleepGoalServiceTest {

    @Mock
    private SleepGoalRepository repository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SleepGoalService service;

    @Test
    void saveObjetivoExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Aaron");
        usuario.setCorreo("aaron@test.com");
        usuario.setRol("ESTUDIANTE");
        SleepGoalRequestDTO dto = new SleepGoalRequestDTO();
        dto.setHorasObjetivo(8.0);
        dto.setDescripcion("Dormir 8 horas diarias");
        dto.setUsuarioId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(repository.findByUsuarioId(1)).thenReturn(Optional.empty());
        when(repository.save(any(SleepGoal.class))).thenAnswer(i -> {
            SleepGoal objetivo = i.getArgument(0);
            objetivo.setId(1);
            return objetivo;
        });
        SleepGoalResponseDTO response = service.saveObjetivo(dto);
        assertNotNull(response);
        assertEquals(8.0, response.getHorasObjetivo());
        assertEquals("Aaron", response.getNombreUsuario());
        verify(repository).save(any(SleepGoal.class));
    }

    @Test
    void saveObjetivoUsuarioYaTieneObjetivo() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Alanis");
        SleepGoal objetivoExistente = new SleepGoal();
        objetivoExistente.setId(1);
        objetivoExistente.setHorasObjetivo(8.0);
        objetivoExistente.setUsuario(usuario);
        SleepGoalRequestDTO dto = new SleepGoalRequestDTO();
        dto.setHorasObjetivo(7.0);
        dto.setDescripcion("Nuevo objetivo");
        dto.setUsuarioId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(repository.findByUsuarioId(1)).thenReturn(Optional.of(objetivoExistente));
        assertThrows(RuntimeException.class, () -> service.saveObjetivo(dto));
    }

    @Test
    void findByUsuarioExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Valeria");
        usuario.setCorreo("valeria@test.com");
        usuario.setRol("ESTUDIANTE");
        SleepGoal objetivo = new SleepGoal();
        objetivo.setId(1);
        objetivo.setHorasObjetivo(8.0);
        objetivo.setDescripcion("Meta principal");
        objetivo.setUsuario(usuario);
        when(repository.findByUsuarioId(1)).thenReturn(Optional.of(objetivo));
        SleepGoalResponseDTO response = service.findByUsuario(1);
        assertNotNull(response);
        assertEquals(8.0, response.getHorasObjetivo());
        assertEquals(1, response.getUsuarioId());
        assertEquals("Valeria", response.getNombreUsuario());
    }
}