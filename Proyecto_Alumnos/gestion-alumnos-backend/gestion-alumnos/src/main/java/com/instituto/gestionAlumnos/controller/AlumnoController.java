package com.instituto.gestionAlumnos.controller;

import com.instituto.gestionAlumnos.model.Alumno;
import com.instituto.gestionAlumnos.service.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumnos")
@CrossOrigin(origins = "http://localhost:3000")
public class AlumnoController {
    
    private final AlumnoService alumnoService;
    
    @Autowired
    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }
    
    @GetMapping
    public ResponseEntity<List<Alumno>> obtenerTodosLosAlumnos() {
        List<Alumno> alumnos = alumnoService.obtenerTodosLosAlumnos();
        return ResponseEntity.ok(alumnos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerAlumnoPorId(@PathVariable Long id) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(id);
        return ResponseEntity.ok(alumno);
    }
    
    @PostMapping
    public ResponseEntity<Alumno> crearAlumno(@Valid @RequestBody Alumno alumno) {
        Alumno nuevoAlumno = alumnoService.crearAlumno(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlumno);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizarAlumno(
            @PathVariable Long id,
            @Valid @RequestBody Alumno alumno) {
        Alumno alumnoActualizado = alumnoService.actualizarAlumno(id, alumno);
        return ResponseEntity.ok(alumnoActualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) {
        alumnoService.eliminarAlumno(id);
        return ResponseEntity.noContent().build();
    }
}