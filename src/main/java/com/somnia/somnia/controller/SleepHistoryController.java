package com.somnia.somnia.controller;

import com.somnia.somnia.service.SleepHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/history")
@CrossOrigin(origins = "*")
public class SleepHistoryController {

    @Autowired
    private SleepHistoryService service;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<?> findHistorialByUsuario(@PathVariable Integer usuarioId) {

        try {
            return ResponseEntity.ok(this.service.findHistorialByUsuario(usuarioId));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{usuarioId}/tracking")
    public ResponseEntity<?> getSeguimiento(@PathVariable Integer usuarioId) {

        try {
            return ResponseEntity.ok(this.service.getSeguimiento(usuarioId));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}