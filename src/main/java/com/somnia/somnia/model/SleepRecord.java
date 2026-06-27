package com.somnia.somnia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_sleep_record")
public class SleepRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sleep_record")
    private Integer id;

    @Column(name = "fecha_registro", nullable = false)
    @NotBlank(message = "La fecha del registro es obligatoria")
    private String fechaRegistro;

    @Column(name = "hora_dormir", nullable = false)
    @NotBlank(message = "La hora de dormir es obligatoria")
    private String horaDormir;

    @Column(name = "hora_despertar", nullable = false)
    @NotBlank(message = "La hora de despertar es obligatoria")
    private String horaDespertar;

    @Column(name = "calidad_sueno", nullable = false)
    @Min(value = 1, message = "La calidad minima es 1")
    @Max(value = 5, message = "La calidad maxima es 5")
    private Integer calidadSueno;

    @Column(name = "observaciones", length = 300)
    private String observaciones;

    @Column(name = "horas_dormidas")
    private Double horasDormidas;

    //@ManyToOne
    //@JoinColumn(name = "id_usuario")
    //private User usuario;

    public SleepRecord() {
    }

    public SleepRecord(Integer id, String fechaRegistro, String horaDormir, String horaDespertar, Integer calidadSueno, String observaciones, Double horasDormidas) {

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

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getHoraDormir() {
        return horaDormir;
    }

    public void setHoraDormir(String horaDormir) {
        this.horaDormir = horaDormir;
    }

    public String getHoraDespertar() {
        return horaDespertar;
    }

    public void setHoraDespertar(String horaDespertar) {
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

    /*
    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
    */
}

