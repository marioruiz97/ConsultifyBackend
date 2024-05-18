package com.asisge.consultifybackend.proyectos.infraestructura.controlador;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.ActividadDto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioActividad;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguridadProyecto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("@seguridadProyecto.esAdmin() or @seguridadProyecto.esMiembroProyecto(#idProyecto, authentication.name)")
@Secured({"ROLE_ADMIN", "ROLE_ASESOR"})
public class ControladorActividad {

    final ServicioSeguridadProyecto seguridadProyecto;
    private final ServicioActividad servicioActividad;

    @Autowired
    public ControladorActividad(ServicioActividad servicioActividad, ServicioSeguridadProyecto seguridadProyecto) {
        this.servicioActividad = servicioActividad;
        this.seguridadProyecto = seguridadProyecto;
    }

    @PostMapping("/proyectos/{idProyecto}/actividades")
    public ResponseEntity<Actividad> crearActividad(@PathVariable Long idProyecto, @Valid @RequestBody ActividadDto nuevaActividad) {
        Actividad actividad = servicioActividad.crearActividad(idProyecto, nuevaActividad);
        return new ResponseEntity<>(actividad, HttpStatus.CREATED);
    }

    @PatchMapping("/proyectos/{idProyecto}/actividades/{idActividad}")
    public ResponseEntity<Actividad> editarActividad(@PathVariable Long idProyecto,
                                                     @PathVariable Long idActividad,
                                                     @Valid @RequestBody ActividadDto nuevaActividad) {
        Actividad actividad = servicioActividad.editarActividad(idProyecto, idActividad, nuevaActividad);
        return new ResponseEntity<>(actividad, HttpStatus.CREATED);
    }

    @DeleteMapping("/proyectos/{idProyecto}/actividades/{idActividad}")
    @ResponseStatus(HttpStatus.OK)
    public void eliminarActividad(@PathVariable Long idProyecto, @PathVariable Long idActividad) {
        servicioActividad.eliminarActividad(idProyecto, idActividad);
    }


}
