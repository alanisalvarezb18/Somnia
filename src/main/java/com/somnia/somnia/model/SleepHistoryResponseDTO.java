package com.somnia.somnia.model;

public class SleepHistoryResponseDTO {

    private Integer totalRegistros;
    private Double promedioHorasDormidas;
    private Double promedioCalidadSueno;
    private Integer registrosCumplidos;
    private Integer registrosNoCumplidos;
    private Double objetivoHoras;

    public SleepHistoryResponseDTO() {
    }

    public SleepHistoryResponseDTO(Integer totalRegistros, Double promedioHorasDormidas, Double promedioCalidadSueno, Integer registrosCumplidos, Integer registrosNoCumplidos, Double objetivoHoras) {

        this.totalRegistros = totalRegistros;
        this.promedioHorasDormidas = promedioHorasDormidas;
        this.promedioCalidadSueno = promedioCalidadSueno;
        this.registrosCumplidos = registrosCumplidos;
        this.registrosNoCumplidos = registrosNoCumplidos;
        this.objetivoHoras = objetivoHoras;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public Double getPromedioHorasDormidas() {
        return promedioHorasDormidas;
    }

    public Double getPromedioCalidadSueno() {
        return promedioCalidadSueno;
    }

    public Integer getRegistrosCumplidos() {
        return registrosCumplidos;
    }

    public Integer getRegistrosNoCumplidos() {
        return registrosNoCumplidos;
    }

    public Double getObjetivoHoras() {
        return objetivoHoras;
    }
}