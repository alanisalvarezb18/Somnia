package com.somnia.somnia.service;

import com.somnia.somnia.model.SleepGoal;
import com.somnia.somnia.model.SleepHistoryResponseDTO;
import com.somnia.somnia.model.SleepRecord;
import com.somnia.somnia.model.SleepRecordResponseDTO;
import com.somnia.somnia.repository.SleepGoalRepository;
import com.somnia.somnia.repository.SleepRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SleepHistoryService {

    @Autowired
    private SleepRecordRepository repository;

    @Autowired
    private SleepGoalRepository sleepGoalRepository;

    @Autowired
    private SleepRecordService sleepRecordService;

    public List<SleepRecordResponseDTO> findHistorialByUsuario(Integer usuarioId) {

        List<SleepRecord> registros = this.repository.findByUsuarioIdOrderByFechaRegistroDesc(usuarioId);

        if (registros.isEmpty()) {
            throw new RuntimeException("El usuario no tiene registros de sueño");
        }

        return this.sleepRecordService.convertirListaDTO(registros);
    }

    public SleepHistoryResponseDTO getSeguimiento(Integer usuarioId) {

        List<SleepRecord> registros = this.repository.findByUsuarioId(usuarioId);

        if (registros.isEmpty()) {
            throw new RuntimeException("El usuario no tiene registros de sueño");
        }

        Optional<SleepGoal> optionalGoal = this.sleepGoalRepository.findByUsuarioId(usuarioId);

        if (optionalGoal.isEmpty()) {
            throw new RuntimeException("El usuario no tiene objetivo de sueño registrado");
        }

        Double objetivoHoras = optionalGoal.get().getHorasObjetivo();

        Double sumaHoras = 0.0;
        Double sumaCalidad = 0.0;
        Integer registrosCumplidos = 0;
        Integer registrosNoCumplidos = 0;

        for (SleepRecord registro : registros) {

            sumaHoras += registro.getHorasDormidas();
            sumaCalidad += registro.getCalidadSueno();

            if (registro.getHorasDormidas() >= objetivoHoras) {
                registrosCumplidos++;
            } else {
                registrosNoCumplidos++;
            }
        }

        Integer totalRegistros = registros.size();

        Double promedioHoras = sumaHoras / totalRegistros;
        Double promedioCalidad = sumaCalidad / totalRegistros;

        return new SleepHistoryResponseDTO(totalRegistros, promedioHoras, promedioCalidad, registrosCumplidos, registrosNoCumplidos, objetivoHoras);
    }
}