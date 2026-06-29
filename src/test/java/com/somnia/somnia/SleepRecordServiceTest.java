package com.somnia.somnia;

import com.somnia.somnia.model.SleepRecord;
import com.somnia.somnia.model.SleepRecordRequestDTO;
import com.somnia.somnia.model.SleepRecordResponseDTO;
import com.somnia.somnia.model.User;
import com.somnia.somnia.repository.SleepRecordRepository;
import com.somnia.somnia.repository.UserRepository;
import com.somnia.somnia.service.SleepRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SleepRecordServiceTest {

    @Mock
    private SleepRecordRepository repository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SleepRecordService service;

    @Test
    void saveRegistroExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Laura");
        usuario.setCorreo("laura@test.com");
        usuario.setRol("ESTUDIANTE");
        SleepRecordRequestDTO dto = new SleepRecordRequestDTO();
        dto.setFechaRegistro(LocalDate.of(2026, 6, 28));
        dto.setHoraDormir(LocalTime.of(23, 30));
        dto.setHoraDespertar(LocalTime.of(6, 45));
        dto.setCalidadSueno(4);
        dto.setObservaciones("Dormí bien");
        dto.setUsuarioId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(repository.save(any(SleepRecord.class))).thenAnswer(i -> {
            SleepRecord registro = i.getArgument(0);
            registro.setId(1);
            return registro;
        });

        SleepRecordResponseDTO response = service.saveRegistro(dto);
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals(7.25, response.getHorasDormidas());
        assertEquals("Laura", response.getNombreUsuario());
        verify(repository).save(any(SleepRecord.class));
    }

    @Test
    void findByIdNoExiste() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(1));
    }

    @Test
    void calcularHorasDormidasCruzaMedianoche() {
        SleepRecord registro = new SleepRecord();
        registro.setHoraDormir(LocalTime.of(23, 30));
        registro.setHoraDespertar(LocalTime.of(6, 45));
        Double horas = service.calcularHorasDormidas(registro);
        assertEquals(7.25, horas);
    }
}