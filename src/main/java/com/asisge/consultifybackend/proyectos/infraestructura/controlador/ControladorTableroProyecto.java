package com.asisge.consultifybackend.proyectos.infraestructura.controlador;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.MiembroDto;
import com.asisge.consultifybackend.proyectos.aplicacion.dto.TableroProyecto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.NotificadorProyecto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguridadProyecto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioTablero;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/tableros")
@PreAuthorize("@seguridadProyecto.esAdmin() or @seguridadProyecto.esMiembroProyecto(#idProyecto, authentication.name)")
public class ControladorTableroProyecto {

    final ServicioSeguridadProyecto seguridadProyecto;
    private final ServicioTablero servicioTablero;
    private final NotificadorProyecto notificadorProyecto;

    @Autowired
    public ControladorTableroProyecto(ServicioTablero servicioTablero,
                                      ServicioSeguridadProyecto seguridadProyecto,
                                      NotificadorProyecto notificadorProyecto) {
        this.servicioTablero = servicioTablero;
        this.seguridadProyecto = seguridadProyecto;
        this.notificadorProyecto = notificadorProyecto;
    }


    @GetMapping("/{idProyecto}")
    public TableroProyecto obtenerProyectoPorId(@PathVariable Long idProyecto) {
        return servicioTablero.obtenerProyectoPorId(idProyecto);
    }


    @GetMapping("/{idProyecto}/posibles-miembros")
    public List<UsuarioAutenticado> obtenerPosiblesMiembrosProyecto(@PathVariable Long idProyecto) {
        return servicioTablero.obtenerPosiblesMiembros(idProyecto);
    }


    @PutMapping("/{idProyecto}/miembros")
    @CacheEvict(value = "informeActividades", key = "#idProyecto")
    public UsuarioAutenticado agregarMiembroAlProyecto(@PathVariable Long idProyecto, @Valid @RequestBody MiembroDto miembroDto) {

        // notificar proyecto y usuario. enviar correo
        UsuarioAutenticado miembro = servicioTablero.agregarMiembroAlProyecto(idProyecto, miembroDto);
        notificadorProyecto.notificarNuevoMiembroProyecto(idProyecto, miembro);

        return miembro;
    }


    @DeleteMapping("/{idProyecto}/miembros/{idMiembro}")
    @CacheEvict(value = "informeActividades", key = "#idProyecto")
    public List<UsuarioAutenticado> quitarMiembroProyecto(@PathVariable Long idProyecto, @PathVariable Long idMiembro) {
        List<UsuarioAutenticado> usuarioAutenticados = servicioTablero.quitarMiembroProyecto(idProyecto, idMiembro);

        // notificar proyecto y usuario
        notificadorProyecto.notificarEliminarMiembroProyecto(idProyecto, idMiembro);

        return usuarioAutenticados;
    }
}
