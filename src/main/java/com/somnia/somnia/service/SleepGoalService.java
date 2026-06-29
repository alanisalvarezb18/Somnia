package com.somnia.somnia.service;

import com.somnia.somnia.model.SleepGoal;
import com.somnia.somnia.model.SleepGoalRequestDTO;
import com.somnia.somnia.model.SleepGoalResponseDTO;
import com.somnia.somnia.model.User;
import com.somnia.somnia.repository.SleepGoalRepository;
import com.somnia.somnia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SleepGoalService {

    @Autowired
    private SleepGoalRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Double redondear(Double valor) {
        if (valor == null) {
            return 0.0;
        }

        return (double) Math.round(valor);
    }

    private void validarHoras(Double horasObjetivo) {
        if (horasObjetivo == null) {
            throw new RuntimeException("Las horas objetivo son obligatorias");
        }

        if (horasObjetivo < 1 || horasObjetivo > 12) {
            throw new RuntimeException("Las horas objetivo deben estar entre 1 y 12");
        }
    }

    public SleepGoalResponseDTO convertirDTO(SleepGoal objetivo) {
        return new SleepGoalResponseDTO(
                objetivo.getId(),
                this.redondear(objetivo.getHorasObjetivo()),
                objetivo.getDescripcion(),
                objetivo.getUsuario().getId(),
                objetivo.getUsuario().getNombre()
        );
    }

    public SleepGoalResponseDTO saveObjetivo(SleepGoalRequestDTO request) {
        if (request.getUsuarioId() == null) {
            throw new RuntimeException("El usuario es obligatorio");
        }

        this.validarHoras(request.getHorasObjetivo());

        Optional<User> optionalUser = this.userRepository.findById(request.getUsuarioId());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        if (this.repository.findByUsuarioId(request.getUsuarioId()).isPresent()) {
            throw new RuntimeException("El usuario ya tiene un objetivo registrado");
        }

        SleepGoal objetivo = new SleepGoal();

        objetivo.setHorasObjetivo(this.redondear(request.getHorasObjetivo()));
        objetivo.setDescripcion(request.getDescripcion());
        objetivo.setUsuario(optionalUser.get());

        return this.convertirDTO(this.repository.save(objetivo));
    }

    public SleepGoalResponseDTO findByUsuario(Integer usuarioId) {
        Optional<SleepGoal> optional = this.repository.findByUsuarioId(usuarioId);

        if (optional.isEmpty()) {
            throw new RuntimeException("El usuario no tiene un objetivo registrado");
        }

        return this.convertirDTO(optional.get());
    }

    public SleepGoalResponseDTO editObjetivo(Integer id, SleepGoalRequestDTO request) {
        Optional<SleepGoal> optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El objetivo no existe");
        }

        SleepGoal objetivo = optional.get();

        if (request.getHorasObjetivo() != null) {
            this.validarHoras(request.getHorasObjetivo());
            objetivo.setHorasObjetivo(this.redondear(request.getHorasObjetivo()));
        }

        if (request.getDescripcion() != null) {
            objetivo.setDescripcion(request.getDescripcion());
        }

        if (request.getUsuarioId() != null) {
            Optional<User> optionalUser = this.userRepository.findById(request.getUsuarioId());

            if (optionalUser.isEmpty()) {
                throw new RuntimeException("El usuario no existe");
            }

            objetivo.setUsuario(optionalUser.get());
        }

        return this.convertirDTO(this.repository.save(objetivo));
    }

    public void deleteObjetivo(Integer id) {
        Optional<SleepGoal> optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El objetivo no existe");
        }

        this.repository.deleteById(id);
    }
}
