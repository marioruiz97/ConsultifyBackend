package com.asisge.consultifybackend.proyectos.infraestructura.controlador;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.ProyectoDto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioProyecto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proyectos")
public class ControladorProyecto {

    private final ServicioProyecto servicioProyecto;

    @Autowired
    public ControladorProyecto(ServicioProyecto servicioProyecto) {
        this.servicioProyecto = servicioProyecto;
    }

    @GetMapping
    public List<Proyecto> obtenerTodosProyectos() {
        return servicioProyecto.obtenerTodos();
    }

    @GetMapping("/{idProyecto}")
    public Proyecto obtenerProyectoPorId(@PathVariable Long idProyecto) {
        return servicioProyecto.obtenerProyectoPorId(idProyecto);
    }

    @PostMapping
    public ResponseEntity<Proyecto> crearProyecto(@Valid @RequestBody ProyectoDto proyecto) {
        Proyecto nuevoProyecto = servicioProyecto.crearProyecto(proyecto);
        return new ResponseEntity<>(nuevoProyecto, HttpStatus.CREATED);
    }

    @PatchMapping("/{idProyecto}")
    public ResponseEntity<Proyecto> editarProyecto(@PathVariable Long idProyecto, @Valid @RequestBody Proyecto proyecto) {
        Proyecto nuevoProyecto = servicioProyecto.editarProyecto(idProyecto, proyecto);
        return new ResponseEntity<>(nuevoProyecto, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{idProyecto}")
    public Boolean eliminarProyecto(@PathVariable Long idProyecto) {
        return servicioProyecto.eliminarProyecto(idProyecto);
    }

}
