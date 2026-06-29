package com.somnia.somnia.controller;

import com.somnia.somnia.model.SleepGoalRequestDTO;
import com.somnia.somnia.service.SleepGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/objetivos")
@CrossOrigin(origins = "*")
public class SleepGoalController {

    @Autowired
    private SleepGoalService service;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> findByUsuario(@PathVariable Integer usuarioId) {

        try {
            return ResponseEntity.ok(this.service.findByUsuario(usuarioId));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveObjetivo(@Validated @RequestBody SleepGoalRequestDTO objetivo, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            return ResponseEntity.ok(this.service.saveObjetivo(objetivo));
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editObjetivo(@Validated @RequestBody SleepGoalRequestDTO objetivo, @PathVariable Integer id, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok(this.service.editObjetivo(id, objetivo));
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteObjetivo(@PathVariable Integer id) {
        try {
            this.service.deleteObjetivo(id);
            return ResponseEntity.ok("El objetivo fue borrado");
        }
        catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}