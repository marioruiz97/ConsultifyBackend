package com.asisge.consultifybackend.proyectos.infraestructura.controlador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioActividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/mis-actividades")
public class ControladorMisActividades {

    private final ServicioActividad servicioActividad;
    private final ServicioAutenticacion servicioAutenticacion;

    @Autowired
    public ControladorMisActividades(ServicioActividad servicioActividad, ServicioAutenticacion servicioAutenticacion) {
        this.servicioActividad = servicioActividad;
        this.servicioAutenticacion = servicioAutenticacion;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Actividad> obtenerMisActividades() {
        @Valid String usernameOCorreo = servicioAutenticacion.obtenerNombreUsuarioEnSesion();
        return servicioActividad.obtenerMisActividades(usernameOCorreo);
    }
}
