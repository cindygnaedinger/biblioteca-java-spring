package com.egg.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egg.biblioteca.entidades.Libro;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarPorTitulo(@Param("titulo") String titulo);
}
