package com.egg.biblioteca.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.repositorios.UsuarioRepositorio;


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
}


