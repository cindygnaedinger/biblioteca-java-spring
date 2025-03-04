package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.EditorialRepositorio;

import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        validar(nombre); 
    Editorial editorial = new Editorial();
    editorial.setNombre(nombre);

    editorialRepositorio.save(editorial);
}

 @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() {


        List<Editorial> editoriales = new ArrayList<>();



        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }

     @Transactional
    public void modificarEditorial(String nombre, Long id){
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }
    }

    private void validar(String nombre) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }
    }
}
