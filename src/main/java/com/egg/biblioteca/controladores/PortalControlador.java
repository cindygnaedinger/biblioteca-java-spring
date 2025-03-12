package com.egg.biblioteca.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo ) {
           if (error != null) {
               modelo.put("error", "Usuario o Contraseña inválidos!");        }
           return "login.html";
       }

       @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
       @GetMapping("/inicio")
       public String inicio() {
           return "inicio.html";
       }
}
