package com.asisge.consultifybackend.auth.infraestructura.configuracion.filtros;

import com.asisge.consultifybackend.auth.aplicacion.manejador.ServicioJWT;
import com.asisge.consultifybackend.auth.dominio.puerto.RepositorioAutorizacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final ServicioJWT jwtService;
    private final RepositorioAutorizacion repositorioAutorizacion;

    @Autowired
    public JWTAuthenticationFilter(ServicioJWT jwtService, RepositorioAutorizacion repositorioAutorizacion) {
        this.jwtService = jwtService;
        this.repositorioAutorizacion = repositorioAutorizacion;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1. Obtener el header que contiene el jwt
        String authHeader = request.getHeader("Authorization"); // Bearer jwt
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


        //2. Obtener jwt desde header
        String jwt = authHeader.split(" ")[1];


        //3. Obtener subject/username desde el jwt
        String nombreUsuario = jwtService.extraerNombreUsuario(jwt);


        //4. Setear un objeto Authentication dentro del SecurityContext
        UsuarioAutenticado usuario = repositorioAutorizacion.buscarPorNombreUsuarioOCorreo(nombreUsuario);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                nombreUsuario, null, Collections.singleton(new SimpleGrantedAuthority(usuario.getRol().name()))
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);


        //5. Ejecutar el restro de filtros
        filterChain.doFilter(request, response);
    }
}
