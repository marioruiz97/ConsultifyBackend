package com.asisge.consultifybackend.notificaciones.infraestructura.controlador;

import com.asisge.consultifybackend.notificaciones.aplicacion.servicio.ServicioNotificacion;
import com.asisge.consultifybackend.notificaciones.dominio.modelo.Notificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@PreAuthorize("isAuthenticated()")
public class ControladorNotificacion {

    private final ServicioNotificacion servicioNotificacion;

    @Autowired
    public ControladorNotificacion(ServicioNotificacion servicioNotificacion) {
        this.servicioNotificacion = servicioNotificacion;
    }


    @GetMapping("/usuarios/{idUsuario}")
    public List<Notificacion> obtenerNotificacionesUsuario(@PathVariable Long idUsuario) {
        return servicioNotificacion.obtenerNotificacionesUsuario(idUsuario);
    }


    @GetMapping("/proyectos/{idProyecto}")
    public List<Notificacion> obtenerNotificacionesProyecto(@PathVariable Long idProyecto) {
        return servicioNotificacion.obtenerNotificacionesProyecto(idProyecto);
    }


    @DeleteMapping("/{idNotificacion}")
    public void eliminarNotificacion(@PathVariable Long idNotificacion) {
        servicioNotificacion.eliminarNotificacion(idNotificacion);
    }
}
