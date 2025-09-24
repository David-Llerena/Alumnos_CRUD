package com.instituto.service;

import com.instituto.model.Alumno;
import com.instituto.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlumnoService {
    
    @Autowired
    private AlumnoRepository alumnoRepository;
    
    // Obtener todos los alumnos
    public List<Alumno> getAllAlumnos() {
        return alumnoRepository.findAll();
    }
    
    // Obtener alumno por ID
    public Optional<Alumno> getAlumnoById(Long id) {
        return alumnoRepository.findById(id);
    }
    
    // Guardar un alumno (crear o actualizar)
    public Alumno saveAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }
    
    // Eliminar un alumno
    public void deleteAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }
    
    // Verificar si existe un correo
    public boolean existeCorreo(String correo) {
        return alumnoRepository.existsByCorreo(correo);
    }
    
    // Obtener alumnos por materia
    public List<Alumno> getAlumnosByMateria(String materia) {
        return alumnoRepository.findByMateria(materia);
    }
    
    // Buscar alumnos por término (nombre, apellido, correo o materia)
    public List<Alumno> buscarAlumnos(String termino) {
        List<Alumno> todosLosAlumnos = alumnoRepository.findAll();
        String terminoLower = termino.toLowerCase().trim();
        
        return todosLosAlumnos.stream()
                .filter(alumno -> 
                    alumno.getNombre().toLowerCase().contains(terminoLower) ||
                    alumno.getApellido().toLowerCase().contains(terminoLower) ||
                    alumno.getCorreo().toLowerCase().contains(terminoLower) ||
                    alumno.getMateria().toLowerCase().contains(terminoLower) ||
                    (alumno.getNombre() + " " + alumno.getApellido()).toLowerCase().contains(terminoLower)
                )
                .collect(Collectors.toList());
    }
    
    // Buscar alumnos por nombre
    public List<Alumno> buscarPorNombre(String nombre) {
        return alumnoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    // Buscar alumnos por apellido
    public List<Alumno> buscarPorApellido(String apellido) {
        return alumnoRepository.findByApellidoContainingIgnoreCase(apellido);
    }
}