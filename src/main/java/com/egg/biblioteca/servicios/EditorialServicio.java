package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.repositorios.EditorialRepositorio;

import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre){
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
}
