package com.instituto.gestionAlumnos.repository;

import com.instituto.gestionAlumnos.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Optional<Alumno> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}