
package com.pepe.ejemplo01springboot.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(value="/home")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @GetMapping
    @ResponseBody
    public String saludo(){
        logger.error("Acaba de llegar al método Saludo (No asustarse por el nivel de logging)");
        return "Hola Mundo desde Spring Boot";
    }

    @PostMapping
    @ResponseBody
    public String despedida(){
        logger.trace("Acaba de ejecutarse el método despedida (es un simple trace)");
        return "Hasta pronto y sigamos practicando.";
    }
}
