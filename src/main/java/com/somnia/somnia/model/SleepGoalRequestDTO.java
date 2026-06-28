package com.somnia.somnia.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SleepGoalRequestDTO {

    @NotNull(message = "Las horas objetivo son obligatorias")
    @Min(value = 1, message = "El objetivo minimo es 1 hora")
    @Max(value = 12, message = "El objetivo maximo es 12 horas")
    private Double horasObjetivo;

    private String descripcion;

    @NotNull(message = "El usuario es obligatorio")
    private Integer usuarioId;

    public SleepGoalRequestDTO() {
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

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}