package com.asisge.consultifybackend.proyectos.infraestructura.controlador;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.ActividadDto;
import com.asisge.consultifybackend.proyectos.aplicacion.dto.CambioEstadoActividadDto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioActividad;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguridadProyecto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("@seguridadProyecto.esAdmin() or @seguridadProyecto.esMiembroProyecto(#idProyecto, authentication.name)")
@RequestMapping("${api.base-path}/proyectos/{idProyecto}/actividades")
@Secured({"ROLE_ADMIN", "ROLE_ASESOR"})
public class ControladorActividad {

    final ServicioSeguridadProyecto seguridadProyecto;
    private final ServicioActividad servicioActividad;

    @Autowired
    public ControladorActividad(ServicioActividad servicioActividad, ServicioSeguridadProyecto seguridadProyecto) {
        this.servicioActividad = servicioActividad;
        this.seguridadProyecto = seguridadProyecto;
    }


    @GetMapping("/{idActividad}")
    public Actividad obtenerActividadPorId(@PathVariable Long idProyecto, @PathVariable Long idActividad) {
        return servicioActividad.obtenerActividadPorId(idProyecto, idActividad);
    }


    @PostMapping
    @CacheEvict(value = "informeActividades", key = "#idProyecto")
    public ResponseEntity<Actividad> crearActividad(@PathVariable Long idProyecto, @Valid @RequestBody ActividadDto nuevaActividad) {
        Actividad actividad = servicioActividad.crearActividad(idProyecto, nuevaActividad);
        return new ResponseEntity<>(actividad, HttpStatus.CREATED);
    }


    @PatchMapping("/{idActividad}")
    @CacheEvict(value = "informeActividades", key = "#idProyecto")
    public ResponseEntity<Actividad> editarActividad(@PathVariable Long idProyecto,
                                                     @PathVariable Long idActividad,
                                                     @Valid @RequestBody ActividadDto nuevaActividad) {
        Actividad actividad = servicioActividad.editarActividad(idProyecto, idActividad, nuevaActividad);
        return new ResponseEntity<>(actividad, HttpStatus.CREATED);
    }


    @PutMapping("/{idActividad}")
    @CacheEvict(value = "informeActividades", key = "#idProyecto")
    public ResponseEntity<Actividad> cambiarEstadoActividad(@PathVariable Long idProyecto,
                                                            @PathVariable Long idActividad,
                                                            @Valid @RequestBody CambioEstadoActividadDto estadoActividadDto) {
        Actividad actividad = servicioActividad.cambiarEstadoActividad(idProyecto, idActividad, estadoActividadDto);
        return new ResponseEntity<>(actividad, HttpStatus.OK);
    }


    @DeleteMapping("/{idActividad}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value = "informeActividades", key = "#idProyecto")
    public void eliminarActividad(@PathVariable Long idProyecto, @PathVariable Long idActividad) {
        servicioActividad.eliminarActividad(idProyecto, idActividad);
    }


}
