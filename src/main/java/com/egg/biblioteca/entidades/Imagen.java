package com.egg.biblioteca.entidades;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Imagen {
    @Id
@UuidGenerator
private String id;
private String mime;
private String nombre;
@Lob
@Basic(fetch = FetchType.LAZY)
@Column(columnDefinition = "LONGBLOB")
private byte[] contenido;

public Imagen() {
}
public String getId() {
return id;
}
public void setId(String id) {
this.id = id;
}
public String getMime() {
return mime;
}
public void setMime(String mime) {
this.mime = mime;
}
public String getNombre() {
return nombre;
}
public void setNombre(String nombre) {
this.nombre = nombre;
}
public byte[] getContenido() {
return contenido;
}
public void setContenido(byte[] contenido) {
this.contenido = contenido;
}
}
