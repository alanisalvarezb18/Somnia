package com.somnia.somnia.service;

import com.somnia.somnia.model.SleepRecord;
import com.somnia.somnia.model.SleepRecordResponseDTO;
import com.somnia.somnia.repository.SleepRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SleepRecordService {

    @Autowired
    private SleepRecordRepository repository;

    public SleepRecordResponseDTO convertirDTO(SleepRecord registro) {

        return new SleepRecordResponseDTO(registro.getId(), registro.getFechaRegistro(), registro.getHoraDormir(), registro.getHoraDespertar(), registro.getCalidadSueno(), registro.getObservaciones(), registro.getHorasDormidas());
    }

    public List<SleepRecordResponseDTO> convertirListaDTO(List<SleepRecord> lista) {

        List<SleepRecordResponseDTO> nuevaLista = new ArrayList<>();

        for (SleepRecord registro : lista) {
            nuevaLista.add(this.convertirDTO(registro));
        }

        return nuevaLista;
    }

    public SleepRecordResponseDTO saveRegistro(SleepRecord registro) {

        Double horasDormidas = this.calcularHorasDormidas(registro);

        if (horasDormidas <= 0) {
            throw new RuntimeException("Las horas dormidas deben ser mayores a cero");
        }

        registro.setHorasDormidas(horasDormidas);

        return this.convertirDTO(this.repository.save(registro));
    }

    public List<SleepRecordResponseDTO> findAll() {

        return this.convertirListaDTO(this.repository.findAllByOrderByFechaRegistroDesc());
    }

    public SleepRecordResponseDTO findById(Integer id) {

        Optional<SleepRecord> optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El registro no existe");
        }

        return this.convertirDTO(optional.get());
    }

    public SleepRecordResponseDTO editRegistro(Integer id, SleepRecord registroEdit) {

        Optional<SleepRecord> optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El registro no existe");
        }

        SleepRecord registro = optional.get();

        Double horasDormidas = this.calcularHorasDormidas(registroEdit);

        if (horasDormidas <= 0) {
            throw new RuntimeException("Las horas dormidas deben ser mayores a cero");
        }

        registro.setFechaRegistro(registroEdit.getFechaRegistro());
        registro.setHoraDormir(registroEdit.getHoraDormir());
        registro.setHoraDespertar(registroEdit.getHoraDespertar());
        registro.setCalidadSueno(registroEdit.getCalidadSueno());
        registro.setObservaciones(registroEdit.getObservaciones());
        registro.setHorasDormidas(horasDormidas);

        return this.convertirDTO(this.repository.save(registro));
    }

    public void deleteRegistro(Integer id) {

        Optional<SleepRecord> optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El registro no existe");
        }

        this.repository.deleteById(id);
    }

    public Double calcularHorasDormidas(SleepRecord registro) {

        double horaDormir = registro.getHoraDormir().getHour() + (registro.getHoraDormir().getMinute() / 60.0);

        double horaDespertar = registro.getHoraDespertar().getHour() + (registro.getHoraDespertar().getMinute() / 60.0);

        if (horaDespertar < horaDormir) {
            horaDespertar += 24;
        }

        return horaDespertar - horaDormir;
    }
}