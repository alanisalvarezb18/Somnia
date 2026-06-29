package com.somnia.somnia.controller;

import com.somnia.somnia.model.User;
import com.somnia.somnia.model.UserResponseDTO;
import com.somnia.somnia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/perfil/{id}")
    public ResponseEntity<?> findPerfil(@PathVariable Integer id, Principal principal) {
        try {
            UserResponseDTO usuario = this.service.findById(id);

            if (!usuario.getCorreo().equals(principal.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo puede consultar su propio perfil");
            }

            return ResponseEntity.ok(usuario);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/perfil/{id}")
    public ResponseEntity<?> editPerfil(@RequestBody User usuario, @PathVariable Integer id, Principal principal) {
        try {
            UserResponseDTO actual = this.service.findById(id);

            if (!actual.getCorreo().equals(principal.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo puede editar su propio perfil");
            }

            usuario.setRol(null);
            return ResponseEntity.ok(this.service.editUsuario(id, usuario));
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(this.service.findById(id));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<?> findByCorreo(@PathVariable String correo) {
        try {
            return ResponseEntity.ok(this.service.findByCorreo(correo));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUsuario(@RequestBody User usuario, @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(this.service.editUsuario(id, usuario));
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer id, Principal principal) {
        try {
            UserResponseDTO usuario = this.service.findById(id);

            if (usuario.getCorreo().equals(principal.getName())) {
                return ResponseEntity.badRequest().body("No puede eliminar el usuario con la sesión iniciada");
            }

            this.service.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
