package com.egg.biblioteca.entidades;

import org.hibernate.annotations.UuidGenerator;

import com.egg.biblioteca.enumeraciones.Rol;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

public class Usuario {
    @Id
    @UuidGenerator
    private String id;

    private String nombre;

    private String email;

    private String password;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
