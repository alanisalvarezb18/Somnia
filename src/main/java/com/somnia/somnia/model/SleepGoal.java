package com.somnia.somnia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_sleep_goal")
public class SleepGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sleep_goal")
    private Integer id;

    @Column(name = "horas_objetivo", nullable = false)
    @NotNull(message = "Las horas objetivo son obligatorias")
    @Min(value = 1, message = "El objetivo minimo es 1 hora")
    @Max(value = 12, message = "El objetivo maximo es 12 horas")
    private Double horasObjetivo;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @OneToOne
    @JoinColumn(name = "id_user", unique = true)
    private User usuario;

    public SleepGoal() {
    }

    public SleepGoal(Integer id, Double horasObjetivo, String descripcion, User usuario) {
        this.id = id;
        this.horasObjetivo = horasObjetivo;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getHorasObjetivo() {
        return horasObjetivo;
    }

    public void setHorasObjetivo(Double horasObjetivo) {
        this.horasObjetivo = horasObjetivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
}