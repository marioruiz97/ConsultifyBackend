package com.asisge.consultifybackend.proyectos.infraestructura.controlador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import com.asisge.consultifybackend.proyectos.aplicacion.dto.ProyectoDto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioProyecto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguridadProyecto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/proyectos")
public class ControladorProyecto {

    final ServicioSeguridadProyecto seguridadProyecto;
    private final ServicioProyecto servicioProyecto;
    private final ServicioAutenticacion servicioAutenticacion;

    @Autowired
    public ControladorProyecto(ServicioProyecto servicioProyecto, ServicioSeguridadProyecto seguridadProyecto, ServicioAutenticacion servicioAutenticacion) {
        this.servicioProyecto = servicioProyecto;
        this.seguridadProyecto = seguridadProyecto;
        this.servicioAutenticacion = servicioAutenticacion;
    }


    @GetMapping
    public List<Proyecto> obtenerTodosProyectos() {

        if (seguridadProyecto.esAdmin()) {
            return servicioProyecto.obtenerTodos();

        } else {
            @Valid String usernameOCorreo = servicioAutenticacion.obtenerNombreUsuarioEnSesion();
            return servicioProyecto.obtenerMisProyectos(usernameOCorreo);
        }

    }


    @PostMapping
    @CacheEvict(value = "informeProyecto", allEntries = true)
    public ResponseEntity<Proyecto> crearProyecto(@Valid @RequestBody ProyectoDto proyecto) {
        Proyecto nuevoProyecto = servicioProyecto.crearProyecto(proyecto);
        return new ResponseEntity<>(nuevoProyecto, HttpStatus.CREATED);
        // notificar proyecto
    }


    @PatchMapping("/{idProyecto}")
    @CacheEvict(value = "informeProyecto", allEntries = true)
    @PreAuthorize("@seguridadProyecto.esAdmin() or @seguridadProyecto.esMiembroProyecto(#idProyecto, authentication.name)")
    public ResponseEntity<Proyecto> editarProyecto(@PathVariable Long idProyecto, @Valid @RequestBody ProyectoDto proyecto) {
        Proyecto proyectoEditado = servicioProyecto.editarProyecto(idProyecto, proyecto);
        return new ResponseEntity<>(proyectoEditado, HttpStatus.CREATED);
    }


    @Secured("ROLE_ADMIN")
    @CacheEvict(value = "informeProyecto", allEntries = true)
    @DeleteMapping("/{idProyecto}")
    public Boolean eliminarProyecto(@PathVariable Long idProyecto) {
        return servicioProyecto.eliminarProyecto(idProyecto);
    }

}
