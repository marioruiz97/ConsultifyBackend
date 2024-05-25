package com.asisge.consultifybackend.proyectos.aplicacion.manejador;

import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguridadProyecto;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioProyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("seguridadProyecto")
public class ManejadorServicioSeguridadProyecto implements ServicioSeguridadProyecto {

    private final RepositorioProyecto repositorioProyecto;

    @Autowired
    public ManejadorServicioSeguridadProyecto(RepositorioProyecto repositorioProyecto) {
        this.repositorioProyecto = repositorioProyecto;
    }

    @Override
    public boolean esMiembroProyecto(Long idProyecto, String correoOUsername) {
        return repositorioProyecto.esMiembroProyecto(correoOUsername);
    }

    @Override
    public boolean esAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }


}
