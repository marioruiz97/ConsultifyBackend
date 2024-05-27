package com.asisge.consultifybackend.proyectos.infraestructura.controlador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import com.asisge.consultifybackend.proyectos.aplicacion.dto.NuevoSeguimientoDto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguimientoActividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Seguimiento;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actividades/{idActividad}/seguimientos")
public class ControladorSeguimiento {

    private final ServicioSeguimientoActividad servicioSeguimientoActividad;

    private final ServicioAutenticacion servicioAutenticacion;

    @Autowired
    public ControladorSeguimiento(ServicioSeguimientoActividad servicioSeguimientoActividad, ServicioAutenticacion servicioAutenticacion) {
        this.servicioSeguimientoActividad = servicioSeguimientoActividad;
        this.servicioAutenticacion = servicioAutenticacion;
    }

    @GetMapping
    public List<Seguimiento> obtenerSeguimientosActividad(@PathVariable Long idActividad) {
        return servicioSeguimientoActividad.obtenerSeguimientos(idActividad);
    }

    @PostMapping
    public ResponseEntity<Seguimiento> crearSeguimientoActividad(@PathVariable Long idActividad, @Valid @RequestBody NuevoSeguimientoDto nuevoSeguimiento) {
        nuevoSeguimiento.setUsername(servicioAutenticacion.obtenerNombreUsuarioEnSesion());
        Seguimiento seguimiento = servicioSeguimientoActividad.crearSeguimiento(idActividad, nuevoSeguimiento);
        return new ResponseEntity<>(seguimiento, HttpStatus.CREATED);
    }

}
