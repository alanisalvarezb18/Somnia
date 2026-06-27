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

        Double horaDormir = Double.parseDouble(registro.getHoraDormir());
        Double horaDespertar = Double.parseDouble(registro.getHoraDespertar());
        Double horasDormidas;

        if (horaDespertar < horaDormir) {

            horasDormidas = (24 - horaDormir) + horaDespertar;

        } else {

            horasDormidas = horaDespertar - horaDormir;
        }

        registro.setHorasDormidas(horasDormidas);

        return this.convertirDTO(this.repository.save(registro));
    }

    public List<SleepRecordResponseDTO> findAll() {

        return this.convertirListaDTO(this.repository.findAll());
    }

    public SleepRecordResponseDTO findById(Integer id) {

        Optional<SleepRecord> optional = this.repository.findById(id);

        if (optional.isEmpty()) {

            throw new RuntimeException("El registro no existe");
        }

        return this.convertirDTO(optional.get());
    }

    public List<SleepRecordResponseDTO> findByUsuario(Integer idUsuario) {

        List<SleepRecord> lista = this.repository.findByUsuarioId(idUsuario);

        if (lista.isEmpty()) {

            throw new RuntimeException("El usuario no tiene registros");
        }

        return this.convertirListaDTO(lista);
    }

    public SleepRecordResponseDTO editRegistro(Integer id, SleepRecord registroEdit) {

        Optional<SleepRecord> optional = this.repository.findById(id);

        if (optional.isEmpty()) {

            throw new RuntimeException("El registro no existe");
        }

        SleepRecord registro = optional.get();

        Double horaDormir = Double.parseDouble(registroEdit.getHoraDormir());
        Double horaDespertar = Double.parseDouble(registroEdit.getHoraDespertar());
        Double horasDormidas;

        if (horaDespertar < horaDormir) {

            horasDormidas = (24 - horaDormir) + horaDespertar;

        } else {

            horasDormidas = horaDespertar - horaDormir;
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
}
