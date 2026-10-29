package com.pepe.ejemplo01springboot.controllers;

import com.pepe.ejemplo01springboot.Models.Persona;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/personas")
@ResponseBody
public class PersonaController {
    @PostMapping
    public String pruebaPost(@RequestBody Persona p){
        System.out.printf("Nombre: %s; Apellido: %s; Edad: %d",p.getNombre(),p.getApellido(),p.getEdad());
        return "ok";
    }
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Persona retornarPersona(){
        return new Persona("Samantha","Moreno",21);
    }
    @ResponseBody
    @GetMapping(value = "/html", produces = MediaType.TEXT_HTML_VALUE)
    public String retornarHtml() {
        return "<h1>Hola Samantha</h1><p>Bienvenida</p>";
    }

}
