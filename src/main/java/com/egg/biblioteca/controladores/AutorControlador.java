package com.egg.biblioteca.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        try {
            autorServicio.crearAutor(nombre); 
            modelo.put("exito", "El autor se registró correctamente.");
        } catch (MiException e) {
            modelo.put("error", "Error al registrar el autor: " + e.getMessage());
            return "autor_form.html";  // Si hay error, vuelve al formulario
        }
        return "index.html";  // Si todo está bien, redirige a la página principal
    }
}

