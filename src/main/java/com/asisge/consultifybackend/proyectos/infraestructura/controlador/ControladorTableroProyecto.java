package com.asisge.consultifybackend.proyectos.infraestructura.controlador;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.MiembroDto;
import com.asisge.consultifybackend.proyectos.aplicacion.dto.TableroProyecto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioTablero;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tableros")
public class ControladorTableroProyecto {

    private final ServicioTablero servicioTablero;

    @Autowired
    public ControladorTableroProyecto(ServicioTablero servicioTablero) {
        this.servicioTablero = servicioTablero;
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
    public UsuarioAutenticado agregarMiembroAlProyecto(@PathVariable Long idProyecto, @Valid @RequestBody MiembroDto miembroDto) {
        return servicioTablero.agregarMiembroAlProyecto(idProyecto, miembroDto);
    }

    @DeleteMapping("/{idProyecto}/miembros/{idMiembro}")
    public List<UsuarioAutenticado> quitarMiembroProyecto(@PathVariable Long idProyecto, @PathVariable Long idMiembro){
        return servicioTablero.quitarMiembroProyecto(idProyecto, idMiembro);
    }
}
