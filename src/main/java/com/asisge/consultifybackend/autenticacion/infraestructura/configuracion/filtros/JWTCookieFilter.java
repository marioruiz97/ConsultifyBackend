package com.asisge.consultifybackend.autenticacion.infraestructura.configuracion.filtros;

import com.asisge.consultifybackend.autenticacion.aplicacion.manejador.ServicioJWT;
import com.asisge.consultifybackend.autenticacion.dominio.puerto.RepositorioAutorizacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JWTCookieFilter extends OncePerRequestFilter {

    private final ServicioJWT jwtService;
    private final RepositorioAutorizacion repositorioAutorizacion;

    @Autowired
    public JWTCookieFilter(ServicioJWT jwtService, RepositorioAutorizacion repositorioAutorizacion) {
        this.jwtService = jwtService;
        this.repositorioAutorizacion = repositorioAutorizacion;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        filterChain.doFilter(request, response);
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtService.validarTokenJwt(jwt)) {
                String nombreUsuario = jwtService.extraerNombreUsuario(jwt);

                UsuarioAutenticado usuario = repositorioAutorizacion.buscarPorNombreUsuarioOCorreo(nombreUsuario);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        nombreUsuario, null, Collections.singleton(new SimpleGrantedAuthority(usuario.getRol().name()))
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
    }

    private String parseJwt(HttpServletRequest request) {
        return jwtService.obtenerJWTDesdeCookie(request);
    }
}
