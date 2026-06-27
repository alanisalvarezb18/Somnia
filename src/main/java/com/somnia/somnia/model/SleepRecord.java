package com.somnia.somnia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_sleep_record")
public class SleepRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sleep_record")
    private Integer id;

    @Column(name = "fecha_registro", nullable = false)
    @NotNull(message = "La fecha del registro es obligatoria")
    private LocalDate fechaRegistro;

    @Column(name = "hora_dormir", nullable = false)
    @NotNull(message = "La hora de dormir es obligatoria")
    private LocalTime horaDormir;

    @Column(name = "hora_despertar", nullable = false)
    @NotNull(message = "La hora de despertar es obligatoria")
    private LocalTime horaDespertar;

    @Column(name = "calidad_sueno", nullable = false)
    @Min(value = 1, message = "La calidad minima es 1")
    @Max(value = 5, message = "La calidad maxima es 5")
    private Integer calidadSueno;

    @Column(name = "observaciones", length = 300)
    private String observaciones;

    @Column(name = "horas_dormidas")
    private Double horasDormidas;

    public SleepRecord() {
    }

    public SleepRecord(Integer id, LocalDate fechaRegistro, LocalTime horaDormir, LocalTime horaDespertar, Integer calidadSueno, String observaciones, Double horasDormidas) {

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

    public void setId(Integer id) {
        this.id = id;
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

    public Double getHorasDormidas() {
        return horasDormidas;
    }

    public void setHorasDormidas(Double horasDormidas) {
        this.horasDormidas = horasDormidas;
    }
}