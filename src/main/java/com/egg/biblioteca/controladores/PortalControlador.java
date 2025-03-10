package com.egg.biblioteca.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @GetMapping("/") // realizo el mapeo
    public String index(){
        return "index.html";
    }

    // Método para mostrar el formulario de registro
    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";  // Retorna la vista para registrarse
    }

    // Método para mostrar la página de inicio de sesión
    @GetMapping("/login")
    public String login() {
        return "login.html";  // Retorna la vista de login
    }
}
