package com.egg.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.biblioteca.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
    
       @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    <List> Autor buscarPorAutor(@Param("autor") String autor);
} 
