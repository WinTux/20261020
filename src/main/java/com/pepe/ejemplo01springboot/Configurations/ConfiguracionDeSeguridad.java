package com.pepe.ejemplo01springboot.Configurations;

import com.pepe.ejemplo01springboot.Filters.ControlConexionFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracionDeSeguridad {
    @Autowired
    private ControlConexionFilter filtro;
    @Bean
    FilterRegistrationBean<Filter> miFiltroRegBean(){
        FilterRegistrationBean<Filter> fil = new FilterRegistrationBean<>();
        fil.setFilter(filtro);
        fil.addUrlPatterns("/estudiantes/*");
        fil.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return fil;
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())
                .build();
    }
}
