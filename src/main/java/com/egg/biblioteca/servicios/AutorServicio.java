package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// import java.util.UUID;

@Service
public class AutorServicio {
    // Inyecta una instancia del repositorio de Autor gestionada por Spring    
    @Autowired
    private AutorRepositorio autorRepositorio;

    //Método para crear un autor
    @Transactional
    public void crearAutor(String nombre) throws MiException{
        
        validar(nombre); //llamo al método validar que asegurará que el nombre no es nulo ni está vacío
        
        Autor autor = new Autor();// Instancio un objeto del tipo Autor
        autor.setNombre(nombre);// Seteo el atributo, con el valor recibido como parámetro

        autorRepositorio.save(autor);// Persisto el dato en mi BBDD
    }

    // Metodo para recuperar una "lista de autores"
    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {

        List<Autor> autores = new ArrayList<>();

        autores = autorRepositorio.findAll();
        return autores;
    }
    
    // Metodo para modificar un autor
    @Transactional
    //Opción con UUID
    // public void modificarAutor(String nombre, UUID id) throws MiException{
    public void modificarAutor(String nombre, String id) throws MiException{    
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) { // Si encuentra el objeto por id
            Autor autor = respuesta.get();
            autor.setNombre(nombre); // Seteo la informacion con el dato recibido
            autorRepositorio.save(autor);

        } else {
            throw new MiException("No se encontró un autor con el ID especificado");
        }
    }
    // Posible método para eliminar autor. Actualmente no implementado en el proyecto
    // @Transactional
    // //Opción con UUID
    // // public void eliminar(UUID id) throws MiException{
    // public void eliminar(String id) throws MiException{
    //     Optional<Autor> autorOpt = autorRepositorio.findById(id);
    //     if (autorOpt.isPresent()) {
    //         autorRepositorio.delete(autorOpt.get());
    //     } else {
    //         throw new MiException("El autor con el ID especificado no existe");
    //     }

    // }

    // Metodo para recuperar un autor por ID
    @Transactional(readOnly = true)
    //Opción con UUID
    // public Autor getOne(UUID id) {
    public Autor getOne(String id) {
        return autorRepositorio.findById(id).orElse(null);
    }
    
    // Metodo para validar que el nombre no sea nulo ni este vacío
    private void validar(String nombre) throws MiException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }
    }
    
}
