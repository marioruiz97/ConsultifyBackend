package com.asisge.consultifybackend.proyectos.aplicacion.manejador;

import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguridadProyecto;
import com.asisge.consultifybackend.actividades.dominio.puerto.RepositorioActividad;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioProyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("seguridadProyecto")
public class ManejadorServicioSeguridadProyecto implements ServicioSeguridadProyecto {

    private final RepositorioProyecto repositorioProyecto;
    private final RepositorioActividad repositorioActividad;

    @Autowired
    public ManejadorServicioSeguridadProyecto(RepositorioProyecto repositorioProyecto, RepositorioActividad repositorioActividad) {
        this.repositorioProyecto = repositorioProyecto;
        this.repositorioActividad = repositorioActividad;
    }

    @Override
    public boolean esMiembroProyecto(Long idProyecto, String correoOUsername) {
        return repositorioProyecto.esMiembroProyecto(idProyecto, correoOUsername);
    }


    @Override
    public boolean esResponsableActividad(Long idActividad, String username) {
        return repositorioActividad.esResponsableActividad(idActividad, username);
    }

    @Override
    public boolean esAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }


}
