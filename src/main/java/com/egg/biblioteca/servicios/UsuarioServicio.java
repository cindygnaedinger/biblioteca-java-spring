package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.egg.biblioteca.entidades.Imagen;
import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.UsuarioRepositorio;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicio implements UserDetailsService{
    // Inyecta una instancia del repositorio de usuario gestionada por Spring    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    // Inyecta una instancia del repositorio de imagen gestionada por Spring    
    @Autowired
    private ImagenServicio imagenServicio;
    
    //Método para registrar un usuario
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiException {

        validar(nombre, email, password, password2);//llamo al método validar lo necesario

        Usuario usuario = new Usuario();// Instancio un objeto del tipo Usuario
        usuario.setNombre(nombre);// Seteo el atributo, con el valor recibido como parámetro
        usuario.setEmail(email);// Seteo el atributo, con el valor recibido como parámetro
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));// Seteo el atributo, con el valor recibido como parámetro
        usuario.setRol(Rol.USER);// Seteo el atributo, con el valor recibido como parámetro

        if (archivo != null && !archivo.isEmpty()) { //Si el archivo existe y no está vacío
            Imagen imagen = imagenServicio.guardar(archivo); //guardo la imagen del usuario
            usuario.setImagen(imagen); //seteo la imagen al usuario
        }

        usuarioRepositorio.save(usuario); //persisto el dato en mi BBDD
    }

    //Método para actualizar usuario
    @Transactional
    //Opción con UUID
    // public void actualizar(MultipartFile archivo, UUID idUsuario, String nombre, String email, String password, String password2) throws MiException {
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String password, String password2) throws MiException {
        validar(nombre, email, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        

        if (respuesta.isEmpty()) {
            throw new MiException("El usuario especificado no existe.");
        }

        Usuario usuario = respuesta.get();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        
        
        // Este condicional verifica si se subió un archivo.
        if (archivo != null && !archivo.isEmpty()) {
            // si el usuario que ya tiene una imagen, obtiene su ID, sino el id será null
            String idImagen = usuario.getImagen() != null ? usuario.getImagen().getId().toString() : null;
             // se actualiza la imagen del usuario o se guarda una nueva imagen si es null
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            // se guarda la imagen actualizada en el usuario
            usuario.setImagen(imagen);
        }

        usuarioRepositorio.save(usuario);
    }

    // Metodo para recuperar una "lista de usuarios"
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    //Método para cambiar el Rol de ADMIN a USER y viceversa
    @Transactional
    //Opción con UUID
    // public void cambiarRol(UUID id){
        public void cambiarRol(String id){
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            if(usuario.getRol().equals(Rol.USER)){
                usuario.setRol(Rol.ADMIN);
            }else if (usuario.getRol().equals(Rol.ADMIN)){
                usuario.setRol(Rol.USER);
            }
            
        }
    }

    //Método para recuperar un "usuario" por su ID
    @Transactional(readOnly = true)
    //Opción con UUID
    // public Usuario getOne(UUID id){
    public Usuario getOne(String id){
        return usuarioRepositorio.findById(id).orElse(null);
    }

    //Método para validar los datos del usuario antes de registrarlo
    private void validar(String nombre, String email, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacío");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
    
    }

    // Este método se encarga de cargar un usuario por su email, autenticarlo y asignarle roles para la seguridad
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca el usuario en la base de datos usando su email
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        
        // Si el usuario existe en la base de datos
        if (usuario != null) {
            // Lista donde se almacenarán los permisos (roles) del usuario
            List<GrantedAuthority> permisos = new ArrayList<>();
            // Crea una autoridad basada en el rol del usuario y la agrega a la lista
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ usuario.getRol().toString());

            permisos.add(p);
            // Obtiene la sesión HTTP actual
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);

            // Guarda el usuario en la sesión con el atributo "usuariosession"
            session.setAttribute("usuariosession", usuario);

            // Retorna un objeto User de Spring Security con el email, contraseña y roles del usuario
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        }else{
            // Si no se encuentra el usuario, retorna null (Spring interpretará esto como usuario no encontrado)
            return null;
        }
    }

    


}




