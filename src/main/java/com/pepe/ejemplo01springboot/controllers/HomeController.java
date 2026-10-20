
package com.pepe.ejemplo01springboot.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(value="/home")
public class HomeController {
    @GetMapping
    @ResponseBody
    public String saludo(){
        return "Hola Mundo desde Spring Boot";
    }

    @PostMapping
    @ResponseBody
    public String despedida(){
        return "Hasta pronto y sigamos practicando.";
    }
}
