package com.pepe.ejemplo01springboot.controllers;

import com.pepe.ejemplo01springboot.Models.Estudiante;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.beans.FeatureDescriptor;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EstudianteController {
    private static Map<String, Estudiante> estudiantes = new HashMap<>();
    static {
        Estudiante e1 = new Estudiante("Pepe","Perales",21,"Contabilidad","est001");
        Estudiante e2 = new Estudiante("Ana","Rocha",21,"Ing. de Sistemas","est008");
        Estudiante e3 = new Estudiante("Sofía","Sosa",21,"Odontología","est201");
        estudiantes.put("1",e1);
        estudiantes.put("2",e2);
        estudiantes.put("3",e3);
    }

    @GetMapping("/estudiantes")
    public ResponseEntity<Object> getEstudiantes(){
        return  new ResponseEntity<>(estudiantes.values(), HttpStatus.OK);
    }
    @GetMapping("/estudiantes/{codigo}")
    public ResponseEntity<Object> getEstudiante(@PathVariable("codigo") String codigo){
        Estudiante est = estudiantes.get(codigo);
        if(est == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(est);
    }
    @PostMapping("/estudiantes")
    public ResponseEntity<Object> nuevoEstudiante(@RequestBody Estudiante e){
        estudiantes.put(e.getCodigo(),e);
        URI ubicacionRecurso = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(e.getCodigo()).toUri();
        return ResponseEntity.created(ubicacionRecurso).build();
    }
    @PutMapping("/estudiantes/{cod}")
    public ResponseEntity<Object> modificarEstudiante(@PathVariable("cod") String codigo, @RequestBody Estudiante e){
        estudiantes.remove(codigo);
        e.setCodigo(codigo);
        estudiantes.put(codigo, e);
        return new ResponseEntity<>("Se actualizaron los datos del estudiante "+codigo, HttpStatus.OK);
    }
    @PatchMapping("/estudiantes/{codigo}")
    public ResponseEntity<Object> modificarEstudianteConPatch(@PathVariable("codigo") String codigo, @RequestBody Map<String,Object> atributosModificados){
        Estudiante estOriginal = estudiantes.get(codigo);
        if(estOriginal == null)
            return ResponseEntity.notFound().build();
        else{
            atributosModificados.forEach((atributo, valorNuevo) -> {
                Field campo = ReflectionUtils.findField(Estudiante.class, atributo);
                if(campo != null){
                    campo.setAccessible(true);
                    ReflectionUtils.setField(campo, estOriginal, valorNuevo);
                }
            });
            estudiantes.remove(codigo);
            estudiantes.put(codigo, estOriginal);
        }
        return new ResponseEntity<>("Se modificó, usando PATCH, al estudiante "+codigo, HttpStatus.OK);
    }
    @DeleteMapping("/estudiantes/{c}")
    public ResponseEntity<Object> eliminarEsetudiante(@PathVariable("c") String cod){
        estudiantes.remove(cod);
        return new ResponseEntity<>("Se eliminó al estudiante "+cod, HttpStatus.OK);
    }
}
