package com.somnia.somnia.service;

import com.somnia.somnia.model.SleepRecord;
import com.somnia.somnia.model.SleepRecordRequestDTO;
import com.somnia.somnia.model.SleepRecordResponseDTO;
import com.somnia.somnia.model.User;
import com.somnia.somnia.repository.SleepRecordRepository;
import com.somnia.somnia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SleepRecordService {

    @Autowired
    private SleepRecordRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Double redondear(Double valor) {
        if (valor == null) {
            return 0.0;
        }

        return (double) Math.round(valor);
    }

    public SleepRecordResponseDTO convertirDTO(SleepRecord registro) {
        return new SleepRecordResponseDTO(
                registro.getId(),
                registro.getFechaRegistro(),
                registro.getHoraDormir(),
                registro.getHoraDespertar(),
                registro.getCalidadSueno(),
                registro.getObservaciones(),
                this.redondear(registro.getHorasDormidas()),
                registro.getUsuario().getId(),
                registro.getUsuario().getNombre()
        );
    }

    public List<SleepRecordResponseDTO> convertirListaDTO(List<SleepRecord> lista) {
        List<SleepRecordResponseDTO> nuevaLista = new ArrayList<>();

        for (SleepRecord registro : lista) {
            nuevaLista.add(this.convertirDTO(registro));
        }
        return nuevaLista;
    }

    public SleepRecord convertirRequest(SleepRecordRequestDTO request) {
        Optional<User> optionalUser = this.userRepository.findById(request.getUsuarioId());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        SleepRecord registro = new SleepRecord();
        registro.setFechaRegistro(request.getFechaRegistro());
        registro.setHoraDormir(request.getHoraDormir());
        registro.setHoraDespertar(request.getHoraDespertar());
        registro.setCalidadSueno(request.getCalidadSueno());
        registro.setObservaciones(request.getObservaciones());
        registro.setUsuario(optionalUser.get());

        return registro;
    }

    public SleepRecordResponseDTO saveRegistro(SleepRecordRequestDTO request) {
        SleepRecord registro = this.convertirRequest(request);
        Double horasDormidas = this.calcularHorasDormidas(registro);

        if (horasDormidas <= 0) {
            throw new RuntimeException("Las horas dormidas deben ser mayores a cero");
        }

        registro.setHorasDormidas(this.redondear(horasDormidas));
        return this.convertirDTO(this.repository.save(registro));
    }

    public List<SleepRecordResponseDTO> findAll() {
        return this.convertirListaDTO(
                this.repository.findAllByOrderByFechaRegistroDesc()
        );
    }

    public SleepRecordResponseDTO findById(Integer id) {
        Optional<SleepRecord> optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El registro no existe");
        }

        return this.convertirDTO(optional.get());
    }

    public List<SleepRecordResponseDTO> findByUsuario(Integer usuarioId) {
        List<SleepRecord> lista = this.repository.findByUsuarioIdOrderByFechaRegistroDesc(usuarioId);

        return this.convertirListaDTO(lista);
    }

    public SleepRecordResponseDTO editRegistro(Integer id, SleepRecordRequestDTO request) {
        Optional<SleepRecord> optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El registro no existe");
        }

        Optional<User> optionalUser = this.userRepository.findById(request.getUsuarioId());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        SleepRecord registro = optional.get();
        registro.setFechaRegistro(request.getFechaRegistro());
        registro.setHoraDormir(request.getHoraDormir());
        registro.setHoraDespertar(request.getHoraDespertar());
        registro.setCalidadSueno(request.getCalidadSueno());
        registro.setObservaciones(request.getObservaciones());
        registro.setUsuario(optionalUser.get());

        Double horasDormidas = this.calcularHorasDormidas(registro);

        if (horasDormidas <= 0) {
            throw new RuntimeException("Las horas dormidas deben ser mayores a cero");
        }

        registro.setHorasDormidas(this.redondear(horasDormidas));

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
        double horaDormir = registro.getHoraDormir().getHour()
                + (registro.getHoraDormir().getMinute() / 60.0);

        double horaDespertar = registro.getHoraDespertar().getHour()
                + (registro.getHoraDespertar().getMinute() / 60.0);

        if (horaDespertar < horaDormir) {
            horaDespertar += 24;
        }

        return horaDespertar - horaDormir;
    }
}
