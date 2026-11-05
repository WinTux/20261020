package com.pepe.ejemplo01springboot.Repositories;

import com.pepe.ejemplo01springboot.Models.Est;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Est, Integer> {
}
