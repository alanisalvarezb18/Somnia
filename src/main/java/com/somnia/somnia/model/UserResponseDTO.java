package com.somnia.somnia.model;

public class UserResponseDTO {

    private Integer id;
    private String nombre;
    private String correo;
    private String rol;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Integer id, String nombre, String correo, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRol() {
        return rol;
    }
}