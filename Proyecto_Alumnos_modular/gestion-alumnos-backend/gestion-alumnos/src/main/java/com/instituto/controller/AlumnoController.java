package com.instituto.controller;

import com.instituto.model.Alumno;
import com.instituto.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumnos")
@CrossOrigin(origins = "http://localhost:3000")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    // Obtener todos los alumnos
    @GetMapping
    public List<Alumno> getAllAlumnos() {
        return alumnoService.getAllAlumnos();
    }

    // Buscar alumnos por término de búsqueda
    @GetMapping("/buscar")
    public List<Alumno> buscarAlumnos(@RequestParam String termino) {
        return alumnoService.buscarAlumnos(termino);
    }

    // Obtener alumnos por materia
    @GetMapping("/materia/{materia}")
    public List<Alumno> getAlumnosByMateria(@PathVariable String materia) {
        return alumnoService.getAlumnosByMateria(materia);
    }

    // Obtener un alumno por ID
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable Long id) {
        Optional<Alumno> alumno = alumnoService.getAlumnoById(id);
        return alumno.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo alumno
    @PostMapping
    public ResponseEntity<Alumno> createAlumno(@RequestBody Alumno alumno) {
        try {
            Alumno nuevoAlumno = alumnoService.saveAlumno(alumno);
            return ResponseEntity.ok(nuevoAlumno);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Actualizar un alumno existente
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> updateAlumno(@PathVariable Long id, @RequestBody Alumno alumnoDetails) {
        Optional<Alumno> alumno = alumnoService.getAlumnoById(id);
        
        if (alumno.isPresent()) {
            Alumno alumnoExistente = alumno.get();
            alumnoExistente.setNombre(alumnoDetails.getNombre());
            alumnoExistente.setApellido(alumnoDetails.getApellido());
            alumnoExistente.setCorreo(alumnoDetails.getCorreo());
            alumnoExistente.setMateria(alumnoDetails.getMateria());
            
            Alumno alumnoActualizado = alumnoService.saveAlumno(alumnoExistente);
            return ResponseEntity.ok(alumnoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un alumno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {
        Optional<Alumno> alumno = alumnoService.getAlumnoById(id);
        
        if (alumno.isPresent()) {
            alumnoService.deleteAlumno(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}