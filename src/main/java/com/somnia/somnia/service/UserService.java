package com.somnia.somnia.service;

import com.somnia.somnia.model.SleepGoal;
import com.somnia.somnia.model.SleepRecord;
import com.somnia.somnia.model.User;
import com.somnia.somnia.model.UserResponseDTO;
import com.somnia.somnia.repository.SleepGoalRepository;
import com.somnia.somnia.repository.SleepRecordRepository;
import com.somnia.somnia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SleepRecordRepository sleepRecordRepository;

    @Autowired
    private SleepGoalRepository sleepGoalRepository;

    public UserResponseDTO convertirDTO(User usuario) {
        return new UserResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getRol());
    }

    public List<UserResponseDTO> convertirListaDTO(List<User> lista) {
        List<UserResponseDTO> nuevaLista = new ArrayList<>();

        for (User usuario : lista) {
            nuevaLista.add(this.convertirDTO(usuario));
        }
        return nuevaLista;
    }

    public UserResponseDTO saveUsuario(User usuario) {
        if (this.repository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya se encuentra registrado");
        }

        if (usuario.getRol() == null || usuario.getRol().isBlank()) {
            usuario.setRol("ESTUDIANTE");
        }

        usuario.setContrasena(this.passwordEncoder.encode(usuario.getContrasena()));
        return this.convertirDTO(this.repository.save(usuario));
    }

    public List<UserResponseDTO> findAll() {
        return this.convertirListaDTO(this.repository.findAll());
    }

    public UserResponseDTO findById(Integer id) {
        Optional<User> optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        return this.convertirDTO(optional.get());
    }

    public UserResponseDTO findByCorreo(String correo) {
        Optional<User> optional = this.repository.findByCorreo(correo);

        if (optional.isEmpty()) {
            throw new RuntimeException("El correo no existe");
        }
        return this.convertirDTO(optional.get());
    }

    public UserResponseDTO login(String correo, String contrasena) {
        Optional<User> optional = this.repository.findByCorreo(correo);

        if (optional.isEmpty()) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }

        User usuario = optional.get();

        if (!this.passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }
        return this.convertirDTO(usuario);
    }

    public UserResponseDTO editUsuario(Integer id, User usuarioEdit) {
        Optional<User> optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        User usuario = optional.get();

        if (!usuario.getCorreo().equals(usuarioEdit.getCorreo()) && this.repository.findByCorreo(usuarioEdit.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya se encuentra registrado");
        }

        usuario.setNombre(usuarioEdit.getNombre());
        usuario.setCorreo(usuarioEdit.getCorreo());

        if (usuarioEdit.getContrasena() != null && !usuarioEdit.getContrasena().isBlank()) {
            usuario.setContrasena(this.passwordEncoder.encode(usuarioEdit.getContrasena()));
        }

        usuario.setRol(usuarioEdit.getRol());
        return this.convertirDTO(this.repository.save(usuario));
    }

    public void deleteUsuario(Integer id) {
        Optional<User> optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        List<SleepRecord> registros = this.sleepRecordRepository.findByUsuarioId(id);

        if (!registros.isEmpty()) {
            this.sleepRecordRepository.deleteAll(registros);
        }

        Optional<SleepGoal> objetivo = this.sleepGoalRepository.findByUsuarioId(id);

        if (objetivo.isPresent()) {
            this.sleepGoalRepository.delete(objetivo.get());
        }

        this.repository.deleteById(id);
    }
}