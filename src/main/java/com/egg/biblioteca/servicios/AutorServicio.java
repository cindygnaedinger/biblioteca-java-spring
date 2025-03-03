package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.repositorios.AutorRepositorio;

import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre){
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
}
