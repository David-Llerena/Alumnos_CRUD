package com.instituto.service;

import com.instituto.model.Materia;
import com.instituto.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaService {
    
    @Autowired
    private MateriaRepository materiaRepository;
    
    // Obtener todas las materias
    public List<Materia> getAllMaterias() {
        return materiaRepository.findAllByOrderByNombreAsc();
    }
    
    // Obtener materia por ID
    public Optional<Materia> getMateriaById(Long id) {
        return materiaRepository.findById(id);
    }
    
    // Obtener materia por nombre
    public Optional<Materia> getMateriaByNombre(String nombre) {
        return materiaRepository.findByNombre(nombre);
    }
    
    // Guardar una materia (crear o actualizar)
    public Materia saveMateria(Materia materia) {
        return materiaRepository.save(materia);
    }
    
    // Eliminar una materia
    public void deleteMateria(Long id) {
        materiaRepository.deleteById(id);
    }
    
    // Verificar si existe una materia por nombre
    public boolean existeNombre(String nombre) {
        return materiaRepository.existsByNombre(nombre);
    }
    
    // Buscar materias por término de búsqueda
    public List<Materia> buscarMaterias(String termino) {
        return materiaRepository.findByNombreContainingIgnoreCase(termino);
    }
    
    // Obtener solo los nombres de las materias (para dropdown)
    public List<String> getNombresMaterias() {
        return getAllMaterias().stream()
                .map(Materia::getNombre)
                .toList();
    }
}