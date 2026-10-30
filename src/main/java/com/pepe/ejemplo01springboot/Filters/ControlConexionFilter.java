package com.pepe.ejemplo01springboot.Filters;

import jakarta.servlet.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class ControlConexionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Desde el Filtro 1");
        System.out.println("Remote host: "+ servletRequest.getRemoteHost());
        System.out.println("Remote address: "+ servletRequest.getRemoteAddr());
        filterChain.doFilter(servletRequest,servletResponse);
        // TODO debemos implementar la clase de configuración.
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
