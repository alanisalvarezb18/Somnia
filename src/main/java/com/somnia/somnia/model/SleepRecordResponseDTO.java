package com.somnia.somnia.model;

public class SleepRecordResponseDTO {

    private Integer id;
    private String fechaRegistro;
    private String horaDormir;
    private String horaDespertar;
    private Integer calidadSueno;
    private String observaciones;
    private Double horasDormidas;
//    private String nombreUsuario;
//    private Integer usuarioId;

    public SleepRecordResponseDTO(Integer id, String fechaRegistro, String horaDormir, String horaDespertar, Integer calidadSueno, String observaciones, Double horasDormidas) {

        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.horaDormir = horaDormir;
        this.horaDespertar = horaDespertar;
        this.calidadSueno = calidadSueno;
        this.observaciones = observaciones;
        this.horasDormidas = horasDormidas;
//        this.nombreUsuario = nombreUsuario;
//        this.usuarioId = usuarioId;
    }

    public Integer getId() {
        return id;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public String getHoraDormir() {
        return horaDormir;
    }

    public String getHoraDespertar() {
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

//    public String getNombreUsuario() {
//        return nombreUsuario;
//    }
//
//    public Integer getUsuarioId() {
//        return usuarioId;
//    }
}
