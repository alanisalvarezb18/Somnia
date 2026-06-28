package com.somnia.somnia.model;

public class SleepGoalResponseDTO {

    private Integer id;
    private Double horasObjetivo;
    private String descripcion;
    private Integer usuarioId;
    private String nombreUsuario;

    public SleepGoalResponseDTO() {
    }

    public SleepGoalResponseDTO(Integer id, Double horasObjetivo, String descripcion, Integer usuarioId, String nombreUsuario) {
        this.id = id;
        this.horasObjetivo = horasObjetivo;
        this.descripcion = descripcion;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getId() {
        return id;
    }

    public Double getHorasObjetivo() {
        return horasObjetivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}