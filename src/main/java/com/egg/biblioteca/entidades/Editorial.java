package com.egg.biblioteca.entidades;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Editorial {
@Id
@UuidGenerator
private String id;

    private String nombre;

    public Editorial() {
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

    @Override
    public String toString() {
        return "Editorial [id=" + id + ", nombre=" + nombre + "]";
    }

    

}
