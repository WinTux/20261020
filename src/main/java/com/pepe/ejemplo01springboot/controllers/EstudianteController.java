package com.pepe.ejemplo01springboot.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.pepe.ejemplo01springboot.Exceptions.EstudianteNoEncontradoException;
import com.pepe.ejemplo01springboot.Models.Estudiante;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EstudianteController {
    ObjectMapper objectMapper = new ObjectMapper();
    private static Map<String, Estudiante> estudiantes = new HashMap<>();
    static {
        Estudiante e1 = new Estudiante("Pepe","Perales",21,"Contabilidad","est001");
        Estudiante e2 = new Estudiante("Ana","Rocha",21,"Ing. de Sistemas","est008");
        Estudiante e3 = new Estudiante("Sofía","Sosa",21,"Odontología","est201");
        estudiantes.put(e1.getCodigo(),e1);
        estudiantes.put(e2.getCodigo(),e2);
        estudiantes.put(e3.getCodigo(),e3);
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
            //return ResponseEntity.notFound().build();
            throw new EstudianteNoEncontradoException();
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
    // TODO Revisar
    @PatchMapping(path="/estudiantes/patch/{codigo}", consumes = "application/json-patch+json")
    public ResponseEntity<String> modificarEstudianteConJsonPatch(@PathVariable("codigo") String codigo, @RequestBody JsonPatch atributosModificados){
        try{
            Estudiante estOriginal = estudiantes.get(codigo);
            JsonNode nodoOriginal = objectMapper.valueToTree(estOriginal);
            JsonNode nodoModificado = atributosModificados.apply(nodoOriginal);
            Estudiante estActualizado = objectMapper.treeToValue(nodoModificado,Estudiante.class);
            estudiantes.remove(codigo);
            estudiantes.put(codigo, estActualizado);
            return new ResponseEntity<>("Se modificó al estudiante "+codigo + " mediante JSON PATCH.",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error fatal al modificar un estudiante con JsonPatch", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/estudiantes/{c}")
    public ResponseEntity<Object> eliminarEsetudiante(@PathVariable("c") String cod){
        estudiantes.remove(cod);
        return new ResponseEntity<>("Se eliminó al estudiante "+cod, HttpStatus.OK);
    }
    @GetMapping("/estudiantes/status")
    public ResponseEntity<String> headersPersonalizados(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Modo-EmpresaX","CRISIS");
        headers.add("Permiso-Cifrado","true");
        headers.add("Cantidad-Amacenes","245");
        return new ResponseEntity<>("Nada",headers,HttpStatus.OK);
    }
    @GetMapping("/estudiantes/edad")
    public ResponseEntity<String> edad(@RequestParam("codigo") String codigo){
        Estudiante est = estudiantes.get(codigo);
        if(est == null){
            return ResponseEntity.badRequest().body("El código que se proporcionó no existe. Este sería un código de estado 404 (se pasa BadRequest para practicar).");
        }
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int edad = est.getEdad();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Fecha-Del-Servidor",fechaActual.format(formateador))
                .header("Codigo-Estudiante",codigo)
                .body("La edad es: "+edad);
    }
    @GetMapping("/estudiantes/pruebacruda")
    public void ejemploCrudo(HttpServletResponse response) throws IOException {
        response.setHeader("Codigo-Depuracion","XZ-400");
        response.setStatus(418);
        response.getWriter().println("Exito");
    }
}
