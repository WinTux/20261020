package com.pepe.ejemplo01springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EjemploApagadoController {
    private static final Logger logger = LoggerFactory.getLogger(EjemploApagadoController.class);
    @Autowired
    private ApplicationContext context;
    @RequestMapping(value="/cerrar")
    public void cerrar(){
        logger.debug("A punto de apagar el servicio!!!");
        SpringApplication.exit(context);
    }
}
