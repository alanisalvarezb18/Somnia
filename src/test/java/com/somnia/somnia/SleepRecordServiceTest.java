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
import java.util.List;

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
    void saveRegistroUsuarioNoExiste() {
        SleepRecordRequestDTO dto = new SleepRecordRequestDTO();
        dto.setFechaRegistro(LocalDate.of(2026, 6, 28));
        dto.setHoraDormir(LocalTime.of(23, 30));
        dto.setHoraDespertar(LocalTime.of(6, 45));
        dto.setCalidadSueno(4);
        dto.setObservaciones("Prueba");
        dto.setUsuarioId(1);

        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.saveRegistro(dto));
        verify(repository, never()).save(any(SleepRecord.class));
    }

    @Test
    void findAllExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Alanis");

        SleepRecord registro = new SleepRecord();
        registro.setId(1);
        registro.setFechaRegistro(LocalDate.of(2026, 6, 28));
        registro.setHoraDormir(LocalTime.of(23, 30));
        registro.setHoraDespertar(LocalTime.of(6, 45));
        registro.setCalidadSueno(4);
        registro.setObservaciones("Dormí bien");
        registro.setHorasDormidas(7.25);
        registro.setUsuario(usuario);
        when(repository.findAllByOrderByFechaRegistroDesc()).thenReturn(List.of(registro));

        List<SleepRecordResponseDTO> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(7.25, response.get(0).getHorasDormidas());
    }

    @Test
    void findByIdExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Valeria");

        SleepRecord registro = new SleepRecord();
        registro.setId(1);
        registro.setFechaRegistro(LocalDate.of(2026, 6, 28));
        registro.setHoraDormir(LocalTime.of(23, 30));
        registro.setHoraDespertar(LocalTime.of(6, 45));
        registro.setCalidadSueno(4);
        registro.setObservaciones("Dormí bien");
        registro.setHorasDormidas(7.25);
        registro.setUsuario(usuario);

        when(repository.findById(1)).thenReturn(Optional.of(registro));
        SleepRecordResponseDTO response = service.findById(1);
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Valeria", response.getNombreUsuario());
    }

    @Test
    void findByIdNoExiste() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(1));
    }

    @Test
    void findByUsuarioExitoso() {
        User usuario = new User();
        usuario.setId(1);
        usuario.setNombre("Laura");

        SleepRecord registro = new SleepRecord();

        registro.setId(1);
        registro.setFechaRegistro(LocalDate.of(2026, 6, 28));
        registro.setHoraDormir(LocalTime.of(23, 30));
        registro.setHoraDespertar(LocalTime.of(6, 45));
        registro.setCalidadSueno(4);
        registro.setHorasDormidas(7.25);
        registro.setUsuario(usuario);

        when(repository.findByUsuarioIdOrderByFechaRegistroDesc(1)).thenReturn(List.of(registro));

        List<SleepRecordResponseDTO> response = service.findByUsuario(1);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Laura", response.get(0).getNombreUsuario());
    }

    @Test
    void findByUsuarioSinRegistros() {
        when(repository.findByUsuarioIdOrderByFechaRegistroDesc(1)).thenReturn(List.of());
        assertThrows(RuntimeException.class, () -> service.findByUsuario(1));
    }

    @Test
    void editRegistroExitoso() {
        User usuarioViejo = new User();
        usuarioViejo.setId(1);
        usuarioViejo.setNombre("Laura");
        User usuarioNuevo = new User();
        usuarioNuevo.setId(1);
        usuarioNuevo.setNombre("Laura");

        SleepRecord registro = new SleepRecord();
        registro.setId(1);
        registro.setFechaRegistro(LocalDate.of(2026, 6, 28));
        registro.setHoraDormir(LocalTime.of(23, 30));
        registro.setHoraDespertar(LocalTime.of(6, 45));
        registro.setCalidadSueno(4);
        registro.setHorasDormidas(7.25);
        registro.setUsuario(usuarioViejo);

        SleepRecordRequestDTO dto = new SleepRecordRequestDTO();
        dto.setFechaRegistro(LocalDate.of(2026, 6, 29));
        dto.setHoraDormir(LocalTime.of(22, 0));
        dto.setHoraDespertar(LocalTime.of(6, 30));
        dto.setCalidadSueno(5);
        dto.setObservaciones("Dormí mejor");
        dto.setUsuarioId(1);

        when(repository.findById(1)).thenReturn(Optional.of(registro));
        when(userRepository.findById(1)).thenReturn(Optional.of(usuarioNuevo));
        when(repository.save(any(SleepRecord.class))).thenAnswer(i -> i.getArgument(0));
        SleepRecordResponseDTO response = service.editRegistro(1, dto);
        assertNotNull(response);
        assertEquals(8.5, response.getHorasDormidas());
        assertEquals(5, response.getCalidadSueno());
    }

    @Test
    void editRegistroNoExiste() {
        SleepRecordRequestDTO dto = new SleepRecordRequestDTO();
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.editRegistro(1, dto));
    }

    @Test
    void deleteRegistroExitoso() {
        SleepRecord registro = new SleepRecord();
        registro.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(registro));
        service.deleteRegistro(1);
        verify(repository).deleteById(1);
    }

    @Test
    void deleteRegistroNoExiste() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.deleteRegistro(1));
        verify(repository, never()).deleteById(any());
    }

    @Test
    void calcularHorasDormidasCruzaMedianoche() {
        SleepRecord registro = new SleepRecord();
        registro.setHoraDormir(LocalTime.of(23, 30));
        registro.setHoraDespertar(LocalTime.of(6, 45));

        Double horas = service.calcularHorasDormidas(registro);
        assertEquals(7.25, horas);
    }

    @Test
    void calcularHorasDormidasMismoDia() {
        SleepRecord registro = new SleepRecord();
        registro.setHoraDormir(LocalTime.of(22, 0));
        registro.setHoraDespertar(LocalTime.of(6, 30));

        Double horas = service.calcularHorasDormidas(registro);

        assertEquals(8.5, horas);
    }
}