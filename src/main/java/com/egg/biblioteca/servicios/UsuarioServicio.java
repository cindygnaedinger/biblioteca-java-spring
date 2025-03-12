package com.egg.biblioteca.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.repositorios.UsuarioRepositorio;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Método para registrar un nuevo usuario
    public void registrarUsuario(String email, String password, String rol) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        // Encriptar la contraseña antes de guardarla
        usuario.setPassword(passwordEncoder.encode(password));

        // Convertir el String a Enum antes de asignarlo
        usuario.setRol(Rol.valueOf(rol.toUpperCase()));

        usuarioRepositorio.save(usuario);
    }

    // Modificación del método loadUserByUsername
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Buscar el usuario por su email
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {
            // Crear lista de permisos/roles
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            // Obtener la sesión HTTP actual y almacenar el objeto Usuario
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);  // Almacenar el usuario en la sesión

            // Retornar el User de Spring Security con las credenciales y roles
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}



