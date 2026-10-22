package com.pepe.ejemplo01springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EjemploApagadoController {
    @Autowired
    private ApplicationContext context;
    @RequestMapping(value="/cerrar")
    public void cerrar(){
        SpringApplication.exit(context);
    }
}
