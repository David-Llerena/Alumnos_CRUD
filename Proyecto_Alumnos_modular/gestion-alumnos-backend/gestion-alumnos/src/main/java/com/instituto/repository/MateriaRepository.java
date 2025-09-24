package com.instituto.repository;

import com.instituto.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    
    // Verificar si existe una materia por nombre
    boolean existsByNombre(String nombre);
    
    // Buscar materia por nombre exacto
    Optional<Materia> findByNombre(String nombre);
    
    // Buscar materias por nombre que contenga el texto (búsqueda parcial)
    List<Materia> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar materias por descripción que contenga el texto
    List<Materia> findByDescripcionContainingIgnoreCase(String descripcion);
    
    // Buscar materias por créditos
    List<Materia> findByCreditos(Integer creditos);
    
    // Ordenar materias por nombre
    List<Materia> findAllByOrderByNombreAsc();
}