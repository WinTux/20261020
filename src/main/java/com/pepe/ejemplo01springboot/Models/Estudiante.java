package com.pepe.ejemplo01springboot.Models;

public class Estudiante extends Persona{
    private String carrera;
    private String codigo;

    public Estudiante() {
    }

    public Estudiante(String nombre, String apellido, int edad, String carrera, String codigo) {
        super(nombre, apellido, edad);
        this.carrera = carrera;
        this.codigo = codigo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
