package com.somnia.somnia.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class SleepRecordRequestDTO {

    @NotNull(message = "La fecha del registro es obligatoria")
    private LocalDate fechaRegistro;

    @NotNull(message = "La hora de dormir es obligatoria")
    private LocalTime horaDormir;

    @NotNull(message = "La hora de despertar es obligatoria")
    private LocalTime horaDespertar;

    @NotNull(message = "La calidad del sueño es obligatoria")
    @Min(value = 1, message = "La calidad minima es 1")
    @Max(value = 5, message = "La calidad maxima es 5")
    private Integer calidadSueno;

    private String observaciones;

    private Integer usuarioId;

    public SleepRecordRequestDTO() {
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalTime getHoraDormir() {
        return horaDormir;
    }

    public void setHoraDormir(LocalTime horaDormir) {
        this.horaDormir = horaDormir;
    }

    public LocalTime getHoraDespertar() {
        return horaDespertar;
    }

    public void setHoraDespertar(LocalTime horaDespertar) {
        this.horaDespertar = horaDespertar;
    }

    public Integer getCalidadSueno() {
        return calidadSueno;
    }

    public void setCalidadSueno(Integer calidadSueno) {
        this.calidadSueno = calidadSueno;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}