package com.instituto.repository;

import com.instituto.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    // Método personalizado para verificar si existe un correo
    boolean existsByCorreo(String correo);

    // Método personalizado para buscar por materia
    List<Alumno> findByMateria(String materia);

    // Método personalizado para buscar por nombre (opcional)
    List<Alumno> findByNombreContainingIgnoreCase(String nombre);

    // Método personalizado para buscar por apellido (opcional)
    List<Alumno> findByApellidoContainingIgnoreCase(String apellido);
}
