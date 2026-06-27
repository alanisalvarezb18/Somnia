package com.somnia.somnia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 80)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(name = "correo", nullable = false, unique = true, length = 100)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ingresar un correo valido")
    private String correo;

    @Column(name = "contrasena", nullable = false)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres")
    private String contrasena;

    @Column(name = "rol", nullable = false, length = 25)
    private String rol;

    public User() {
    }

    public User(Integer id, String nombre, String correo, String contrasena, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}