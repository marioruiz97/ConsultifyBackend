package com.asisge.consultifybackend.usuarios.infraestructura.controlador;


import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguridadProyecto;
import com.asisge.consultifybackend.usuarios.aplicacion.dto.CambioEstadoDto;
import com.asisge.consultifybackend.usuarios.aplicacion.dto.NuevoUsuarioAutenticadoDto;
import com.asisge.consultifybackend.usuarios.aplicacion.dto.UsuarioListaDto;
import com.asisge.consultifybackend.usuarios.aplicacion.servicio.ServicioUsuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Rol;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.utilidad.dominio.excepcion.AccionNoPermitidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/usuarios")
public class ControladorUsuario {

    private final ServicioUsuario servicioUsuario;
    private final ServicioSeguridadProyecto servicioSeguridad;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario, ServicioSeguridadProyecto servicioSeguridad) {
        this.servicioUsuario = servicioUsuario;
        this.servicioSeguridad = servicioSeguridad;
    }

    @GetMapping
    public List<UsuarioListaDto> buscarUsuarios() {
        return servicioUsuario.buscarTodos();
    }

    @GetMapping("/{idUsuario}")
    public UsuarioAutenticado obternerPorId(@PathVariable Long idUsuario) {
        UsuarioAutenticado usuario = servicioUsuario.buscarUsuarioPorId(idUsuario);
        if (!servicioSeguridad.esAdmin() && usuario.getRol().equals(Rol.ROLE_ADMIN))
            throw new AccionNoPermitidaException("No tiene permisos para acceder a los datos del usuario");
        return usuario;

    }

    @PostMapping
    public ResponseEntity<UsuarioAutenticado> crearUsuario(@RequestBody NuevoUsuarioAutenticadoDto nuevoUsuario) {
        return new ResponseEntity<>(servicioUsuario.crearUsuarioAutenticado(nuevoUsuario), HttpStatus.CREATED);
    }

    @PatchMapping("/{idUsuario}")
    public ResponseEntity<UsuarioAutenticado> editarInformacionBasica(@PathVariable Long idUsuario, @RequestBody NuevoUsuarioAutenticadoDto editarUsuario) {
        return new ResponseEntity<>(servicioUsuario.editarInformacionBasica(idUsuario, editarUsuario), HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/estado/{idUsuario}")
    public ResponseEntity<Boolean> cambiarEstado(@PathVariable Long idUsuario, @RequestBody CambioEstadoDto estadoDto) {
        Boolean nuevoEstado = servicioUsuario.cambiarEstado(idUsuario, estadoDto.getActivo());
        return ResponseEntity.ok(nuevoEstado);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{idUsuario}")
    public Boolean desactivarUsuario(@PathVariable Long idUsuario) {
        return servicioUsuario.adminDesactivaUsuario(idUsuario);
    }

}
