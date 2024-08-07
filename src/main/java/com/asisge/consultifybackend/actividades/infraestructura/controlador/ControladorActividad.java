package com.asisge.consultifybackend.actividades.infraestructura.controlador;

import com.asisge.consultifybackend.actividades.aplicacion.dto.ActividadDto;
import com.asisge.consultifybackend.actividades.aplicacion.dto.CambioEstadoActividadDto;
import com.asisge.consultifybackend.actividades.aplicacion.servicio.NotificadorActividad;
import com.asisge.consultifybackend.actividades.aplicacion.servicio.ServicioActividad;
import com.asisge.consultifybackend.actividades.dominio.modelo.Actividad;
import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguridadProyecto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("@seguridadProyecto.esAdmin() or @seguridadProyecto.esMiembroProyecto(#idProyecto, authentication.name)")
@RequestMapping("${api.base-path}/proyectos/{idProyecto}/actividades")
public class ControladorActividad {

    final ServicioSeguridadProyecto seguridadProyecto;
    private final ServicioActividad servicioActividad;
    private final NotificadorActividad notificador;
    private final ServicioAutenticacion servicioAutenticacion;

    @Autowired
    public ControladorActividad(ServicioActividad servicioActividad, ServicioSeguridadProyecto seguridadProyecto,
                                NotificadorActividad notificador, ServicioAutenticacion servicioAutenticacion) {
        this.servicioActividad = servicioActividad;
        this.seguridadProyecto = seguridadProyecto;
        this.notificador = notificador;
        this.servicioAutenticacion = servicioAutenticacion;
    }


    @GetMapping("/{idActividad}")
    public Actividad obtenerActividadPorId(@PathVariable Long idProyecto, @PathVariable Long idActividad) {
        return servicioActividad.obtenerActividadPorId(idProyecto, idActividad);
    }


    @PostMapping
    @CacheEvict(value = {"informeActividades", "archivoInforme"}, key = "#idProyecto")
    public ResponseEntity<Actividad> crearActividad(@PathVariable Long idProyecto, @Valid @RequestBody ActividadDto nuevaActividad) {
        Actividad actividad = servicioActividad.crearActividad(idProyecto, nuevaActividad);

        // notificar usuario responsable actividad. enviar correo si no es quien la creo
        String username = servicioAutenticacion.obtenerNombreUsuarioEnSesion();
        notificador.notificarResponsableActividad(actividad, nuevaActividad, username);

        return new ResponseEntity<>(actividad, HttpStatus.CREATED);
    }


    @PatchMapping("/{idActividad}")
    @CacheEvict(value = {"informeActividades", "archivoInforme"}, key = "#idProyecto")
    @PreAuthorize("@seguridadProyecto.esAdmin() or @seguridadProyecto.esResponsableActividad(#idActividad, authentication.name)")
    public ResponseEntity<Actividad> editarActividad(@PathVariable Long idProyecto,
                                                     @PathVariable Long idActividad,
                                                     @Valid @RequestBody ActividadDto nuevaActividad) {
        Actividad actividad = servicioActividad.editarActividad(idProyecto, idActividad, nuevaActividad);
        return new ResponseEntity<>(actividad, HttpStatus.CREATED);
    }


    @PutMapping("/{idActividad}")
    @CacheEvict(value = {"informeActividades", "archivoInforme"}, key = "#idProyecto")
    @PreAuthorize("@seguridadProyecto.esAdmin() or @seguridadProyecto.esResponsableActividad(#idActividad, authentication.name)")
    public ResponseEntity<Actividad> cambiarEstadoActividad(@PathVariable Long idProyecto,
                                                            @PathVariable Long idActividad,
                                                            @Valid @RequestBody CambioEstadoActividadDto estadoActividadDto) {
        Actividad actividad = servicioActividad.cambiarEstadoActividad(idProyecto, idActividad, estadoActividadDto);
        return new ResponseEntity<>(actividad, HttpStatus.OK);
    }


    @DeleteMapping("/{idActividad}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value = {"informeActividades", "archivoInforme"}, key = "#idProyecto")
    @PreAuthorize("@seguridadProyecto.esAdmin() or @seguridadProyecto.esResponsableActividad(#idActividad, authentication.name)")
    public void eliminarActividad(@PathVariable Long idProyecto, @PathVariable Long idActividad) {
        servicioActividad.eliminarActividad(idProyecto, idActividad);
    }


}
