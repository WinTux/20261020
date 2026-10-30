package com.pepe.ejemplo01springboot.Configurations;

import com.pepe.ejemplo01springboot.Interceptors.EstudianteInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class EstudianteInterceptorAppConfiguration implements WebMvcConfigurer {
    @Autowired
    private EstudianteInterceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(interceptor);
    }
}
