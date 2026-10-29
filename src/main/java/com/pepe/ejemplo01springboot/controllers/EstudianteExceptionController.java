package com.pepe.ejemplo01springboot.controllers;

import com.pepe.ejemplo01springboot.Exceptions.EstudianteNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EstudianteExceptionController {
    @ExceptionHandler(value = EstudianteNoEncontradoException.class)
    public ResponseEntity<Object> unaExcepcion(EstudianteNoEncontradoException ex){
        return new ResponseEntity<>("No se encontró al estudiante", HttpStatus.NOT_FOUND);
    }
}
