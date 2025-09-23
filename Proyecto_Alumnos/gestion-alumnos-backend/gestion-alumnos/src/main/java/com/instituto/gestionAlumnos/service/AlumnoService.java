package com.instituto.gestionAlumnos.service;

import com.instituto.gestionAlumnos.exception.ResourceNotFoundException;
import com.instituto.gestionAlumnos.exception.DuplicateResourceException;
import com.instituto.gestionAlumnos.model.Alumno;
import com.instituto.gestionAlumnos.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlumnoService {
    
    private final AlumnoRepository alumnoRepository;
    
    @Autowired
    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }
    
    public List<Alumno> obtenerTodosLosAlumnos() {
        return alumnoRepository.findAll();
    }
    
    public Alumno obtenerAlumnoPorId(Long id) {
        return alumnoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Alumno no encontrado con id: " + id
            ));
    }
    
    public Alumno crearAlumno(Alumno alumno) {
        if (alumnoRepository.existsByCorreo(alumno.getCorreo())) {
            throw new DuplicateResourceException(
                "Ya existe un alumno con el correo: " + alumno.getCorreo()
            );
        }
        return alumnoRepository.save(alumno);
    }
    
    public Alumno actualizarAlumno(Long id, Alumno alumnoActualizado) {
        Alumno alumnoExistente = obtenerAlumnoPorId(id);
        
        if (!alumnoExistente.getCorreo().equals(alumnoActualizado.getCorreo()) 
            && alumnoRepository.existsByCorreo(alumnoActualizado.getCorreo())) {
            throw new DuplicateResourceException(
                "Ya existe un alumno con el correo: " + alumnoActualizado.getCorreo()
            );
        }
        
        alumnoExistente.setNombre(alumnoActualizado.getNombre());
        alumnoExistente.setApellido(alumnoActualizado.getApellido());
        alumnoExistente.setCorreo(alumnoActualizado.getCorreo());
        alumnoExistente.setMateria(alumnoActualizado.getMateria());
        
        return alumnoRepository.save(alumnoExistente);
    }
    
    public void eliminarAlumno(Long id) {
        Alumno alumno = obtenerAlumnoPorId(id);
        alumnoRepository.delete(alumno);
    }
}