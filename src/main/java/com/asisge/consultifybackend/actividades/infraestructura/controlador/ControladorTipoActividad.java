package com.asisge.consultifybackend.actividades.infraestructura.controlador;

import com.asisge.consultifybackend.actividades.aplicacion.servicio.ServicioTipoActividad;
import com.asisge.consultifybackend.actividades.dominio.modelo.TipoActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/tipo-actividad")
public class ControladorTipoActividad {

    private final ServicioTipoActividad servicioTipoActividad;

    @Autowired
    public ControladorTipoActividad(ServicioTipoActividad servicioTipoActividad) {
        this.servicioTipoActividad = servicioTipoActividad;
    }


    @GetMapping
    public List<TipoActividad> listarTipoActividad() {
        return servicioTipoActividad.obtenerTodos();
    }

    @PostMapping
    public ResponseEntity<TipoActividad> crearTipoActividad(@RequestBody TipoActividad tipoActividad) {
        return new ResponseEntity<>(servicioTipoActividad.crearTipoActividad(tipoActividad), HttpStatus.CREATED);
    }

    @PatchMapping("/{idTipo}")
    public ResponseEntity<TipoActividad> editarTipoActividad(@PathVariable Long idTipo, @RequestBody TipoActividad tipoActividad) {
        tipoActividad.setIdTipo(idTipo);
        return new ResponseEntity<>(servicioTipoActividad.editarTipoActividad(tipoActividad), HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{idTipo}")
    public ResponseEntity<Void> eliminarTipoActividad(@PathVariable Long idTipo) {
        servicioTipoActividad.eliminarTipoActividad(idTipo);
        return ResponseEntity.ok().build();
    }


}
