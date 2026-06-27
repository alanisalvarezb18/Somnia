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

    public SleepRecordResponseDTO(Integer id, LocalDate fechaRegistro, LocalTime horaDormir, LocalTime horaDespertar, Integer calidadSueno, String observaciones, Double horasDormidas) {

        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.horaDormir = horaDormir;
        this.horaDespertar = horaDespertar;
        this.calidadSueno = calidadSueno;
        this.observaciones = observaciones;
        this.horasDormidas = horasDormidas;
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
}