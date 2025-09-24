package com.instituto.controller;

import com.instituto.model.Materia;
import com.instituto.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/materias")
@CrossOrigin(origins = "http://localhost:3000")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    // Obtener todas las materias
    @GetMapping
    public List<Materia> getAllMaterias() {
        return materiaService.getAllMaterias();
    }

    // Obtener solo los nombres de materias (para dropdown)
    @GetMapping("/nombres")
    public List<String> getNombresMaterias() {
        return materiaService.getNombresMaterias();
    }

    // Obtener una materia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Materia> getMateriaById(@PathVariable Long id) {
        Optional<Materia> materia = materiaService.getMateriaById(id);
        return materia.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // Buscar materias por término
    @GetMapping("/buscar")
    public List<Materia> buscarMaterias(@RequestParam String termino) {
        return materiaService.buscarMaterias(termino);
    }

    // Crear una nueva materia
    @PostMapping
    public ResponseEntity<Materia> createMateria(@RequestBody Materia materia) {
        try {
            // Verificar si ya existe una materia con ese nombre
            if (materiaService.existeNombre(materia.getNombre())) {
                return ResponseEntity.badRequest().build();
            }
            
            Materia nuevaMateria = materiaService.saveMateria(materia);
            return ResponseEntity.ok(nuevaMateria);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Actualizar una materia existente
    @PutMapping("/{id}")
    public ResponseEntity<Materia> updateMateria(@PathVariable Long id, @RequestBody Materia materiaDetails) {
        Optional<Materia> materia = materiaService.getMateriaById(id);
        
        if (materia.isPresent()) {
            Materia materiaExistente = materia.get();
            
            // Verificar si el nuevo nombre ya existe (excepto para la materia actual)
            if (!materiaExistente.getNombre().equals(materiaDetails.getNombre()) && 
                materiaService.existeNombre(materiaDetails.getNombre())) {
                return ResponseEntity.badRequest().build();
            }
            
            materiaExistente.setNombre(materiaDetails.getNombre());
            materiaExistente.setDescripcion(materiaDetails.getDescripcion());
            materiaExistente.setCreditos(materiaDetails.getCreditos());
            
            Materia materiaActualizada = materiaService.saveMateria(materiaExistente);
            return ResponseEntity.ok(materiaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una materia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMateria(@PathVariable Long id) {
        Optional<Materia> materia = materiaService.getMateriaById(id);
        
        if (materia.isPresent()) {
            materiaService.deleteMateria(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}