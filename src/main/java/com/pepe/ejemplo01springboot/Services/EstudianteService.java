package com.pepe.ejemplo01springboot.Services;

import com.pepe.ejemplo01springboot.Models.Est;
import com.pepe.ejemplo01springboot.Repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository estudianteRepo;

    public List<Est> lista(){
        return estudianteRepo.findAll();
    }

    public Est getEstudiantePorMatricula(int m){
        return estudianteRepo.findById(m).orElse(null);
    }
}
