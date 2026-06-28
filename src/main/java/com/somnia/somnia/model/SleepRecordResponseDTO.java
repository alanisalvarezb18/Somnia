package com.somnia.somnia.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class SleepRecordResponseDTO {

    private Integer id;
    private LocalDate fechaRegistro;
    private LocalTime horaDormir;
    private LocalTime horaDespertar;
    private Integer calidadSueno;
    private String observaciones;
    private Double horasDormidas;
    private Integer usuarioId;
    private String nombreUsuario;

    public SleepRecordResponseDTO(Integer id, LocalDate fechaRegistro, LocalTime horaDormir, LocalTime horaDespertar, Integer calidadSueno, String observaciones, Double horasDormidas, Integer usuarioId, String nombreUsuario) {

        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.horaDormir = horaDormir;
        this.horaDespertar = horaDespertar;
        this.calidadSueno = calidadSueno;
        this.observaciones = observaciones;
        this.horasDormidas = horasDormidas;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public LocalTime getHoraDormir() {
        return horaDormir;
    }

    public LocalTime getHoraDespertar() {
        return horaDespertar;
    }

    public Integer getCalidadSueno() {
        return calidadSueno;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Double getHorasDormidas() {
        return horasDormidas;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}