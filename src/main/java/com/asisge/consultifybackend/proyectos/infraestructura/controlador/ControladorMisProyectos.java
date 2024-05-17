package com.asisge.consultifybackend.proyectos.infraestructura.controlador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioProyecto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mis-proyectos")
public class ControladorMisProyectos {

    private final ServicioProyecto servicioProyecto;
    private final ServicioAutenticacion servicioAutenticacion;

    @Autowired
    public ControladorMisProyectos(ServicioProyecto servicioProyecto, ServicioAutenticacion servicioAutenticacion) {
        this.servicioProyecto = servicioProyecto;
        this.servicioAutenticacion = servicioAutenticacion;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Proyecto> obtenerMisProyectos() {
        @Valid String usernameOCorreo = servicioAutenticacion.obtenerNombreUsuarioEnSesion();
        return servicioProyecto.obtenerMisProyectos(usernameOCorreo);
    }

}
