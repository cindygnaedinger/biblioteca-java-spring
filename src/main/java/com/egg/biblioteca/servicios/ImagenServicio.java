package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Imagen;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.ImagenRepositorio;
import java.util.List;
import java.util.Optional;
// import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {
    // Inyecta una instancia del repositorio de Imagen gestionada por Spring    
    @Autowired
    private ImagenRepositorio imagenRepositorio;
    
    //Método para guardar imagen
    public Imagen guardar(MultipartFile archivo) throws MiException{
        if (archivo != null) {
            try {
                
                Imagen imagen = new Imagen(); // Instancio un objeto del tipo Imágen
                
                imagen.setMime(archivo.getContentType());// Seteo el atributo, con el valor recibido como parámetro
                imagen.setNombre(archivo.getName()); // Seteo el atributo, con el valor recibido como parámetro
                imagen.setContenido(archivo.getBytes()); // Seteo el atributo, con el valor recibido como parámetro
                
                return imagenRepositorio.save(imagen); // Persisto el dato en mi BBDD
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    //Método para actualizar imagen
    @Transactional
    //Opción con UUID
    // public Imagen actualizar(MultipartFile archivo, UUID idImagen) throws MiException{
    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException{
        if (archivo != null) {
            try {
                
                Imagen imagen = new Imagen();
                
                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
                return imagenRepositorio.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
        
    }
    
    //Método para listar todas las imágenes
    @Transactional(readOnly = true)
	public List<Imagen> listarTodos() {
		return imagenRepositorio.findAll();
	}
    
}