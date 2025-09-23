package com.instituto.gestionAlumnos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "alumnos")
public class Alumno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    private Long idAlumno;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "apellido", nullable = false)
    private String apellido;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    @Column(name = "correo", unique = true, nullable = false)
    private String correo;
    
    @NotBlank(message = "La materia es obligatoria")
    @Column(name = "materia", nullable = false)
    private String materia;

    // Constructor vacío
    public Alumno() {
    }

    // Constructor con todos los campos
    public Alumno(String nombre, String apellido, String correo, String materia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.materia = materia;
    }

    // Getters y Setters
    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
}