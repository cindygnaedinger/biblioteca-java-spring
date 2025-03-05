package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;

import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException{
        validar(nombre); 
        Autor autor = new Autor(); // acá instancio un objeto del tipo autor
        autor.setNombre(nombre); // seteo el atributo

        autorRepositorio.save(autor); // persisto el dato en mi bd
    }

     @Transactional(readOnly = true)
    public List<Autor> listarAutores() {


        List<Autor> autores = new ArrayList<>();



        autores = autorRepositorio.findAll();
        return autores;
    }

    @Transactional
    public void modificarAutor(String nombre, String id){
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
    }
    
    private void validar(String nombre) throws MiException {
        
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
    }
}
