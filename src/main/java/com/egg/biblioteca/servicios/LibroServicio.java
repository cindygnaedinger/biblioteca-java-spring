package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;

import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, Long idAutor, Long idEditorial)throws MiException{
        validar(isbn, titulo, ejemplares, idAutor, idEditorial); 
        Optional<Autor> autorOptional = autorRepositorio.findById(idAutor);
        if(autorOptional.isEmpty()){
            throw new IllegalArgumentException("No se encontró el autor con el ID proporcionado.");
        }
        Autor autor = autorOptional.get();

        // Buscar el autor por ID
        Optional<Editorial> editorialOptional = editorialRepositorio.findById(idEditorial);
        if(editorialOptional.isEmpty()){
            throw new IllegalArgumentException("No se encontró la editorial con el ID proporcionado.");
        }
        Editorial editorial = editorialOptional.get();

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setAlta(new Date());
    
        // guardo en la bd
        libroRepositorio.save(libro);
    }

     @Transactional(readOnly = true)
    public List<Libro> listarLibros() {


        List<Libro> libros = new ArrayList<>();



        libros = libroRepositorio.findAll();
        return libros;
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, Long idAutor, Long idEditorial) throws MiException {
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if(respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }

        if(respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }

        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
        }
    }

    private void validar(Long isbn, String titulo, Integer ejemplares, Long idAutor, Long idEditorial) throws MiException {
        if (isbn == null) {
            throw new MiException("El ISBN no puede ser nulo.");
        }
        if (titulo.isEmpty() || titulo == null){
            throw new MiException("El título no puede ser nulo o estar vacío.");
        }
        if(ejemplares == null){
            throw new MiException("Ejemplares no puede ser nulo.");
        }
        if (idAutor == null) {
            throw new MiException("El ID de autor no puede ser nulo o estar vacío.");
        }
        if (idEditorial == null) {
            throw new MiException("El ID de editorial no puede ser nulo o estar vacío.");
        }
    }
}
