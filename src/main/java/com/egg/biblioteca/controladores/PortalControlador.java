package com.egg.biblioteca.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @GetMapping("/") // Realizo el mapeo
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
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña inválidos!");
        }
        return "login.html";  // Retorna la vista de login
    }

    // Método para la vista de inicio, sólo accesible para usuarios con los roles 'USER' o 'ADMIN'
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        
        // Verificación del rol del usuario, redirige si es ADMIN
        if (logueado != null && "ADMIN".equals(logueado.getRol().toString())) {
            return "redirect:/admin/dashboard";  // Redirige al dashboard de administración
        }
        
        return "inicio.html";  // Retorna la vista de inicio para usuarios normales
    }
}

