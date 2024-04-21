package com.asisge.consultifybackend.usuarios.infraestructura.controlador;


import com.asisge.consultifybackend.usuarios.aplicacion.dto.*;
import com.asisge.consultifybackend.usuarios.aplicacion.servicio.ServicioUsuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {

    private final ServicioUsuario manejadorServicioUsuario;

    @Autowired
    public ControladorUsuario(ServicioUsuario manejadorServicioUsuario) {
        this.manejadorServicioUsuario = manejadorServicioUsuario;
    }

    @GetMapping
    public List<UsuarioListaDto> buscarUsuarios() {
        return manejadorServicioUsuario.buscarTodos();
    }


    @GetMapping("/{identificacion}")
    public UsuarioAutenticado obternerPorIdentificacion(@PathVariable("identificacion") String identificacion) {
        return manejadorServicioUsuario.buscarUsuarioPorIdentificacion(identificacion);
    }

    @PostMapping()
    public ResponseEntity<UsuarioAutenticado> crearUsuario(@RequestBody NuevoUsuarioAutenticadoDto nuevoUsuario) {
        return new ResponseEntity<>(manejadorServicioUsuario.crearUsuarioAutenticado(nuevoUsuario), HttpStatus.CREATED);
    }

    @PatchMapping("/{idUsuario}")
    public ResponseEntity<UsuarioAutenticado> editarInformacionBasica(@PathVariable Long idUsuario, @RequestBody UsuarioBasicoDto editarUsuario) {
        return new ResponseEntity<>(manejadorServicioUsuario.editarInformacionBasica(idUsuario, editarUsuario), HttpStatus.CREATED);
    }

    @PatchMapping("/perfil/correo/{idUsuario}")
    public ResponseEntity<UsuarioAutenticado> cambiarCorreoElectronico(@PathVariable Long idUsuario, @RequestBody CambioCorreoDto usuarioDto) {
        return new ResponseEntity<>(manejadorServicioUsuario.cambiarCorreoElectronico(idUsuario, usuarioDto), HttpStatus.OK);
    }

    @PatchMapping("/perfil/contrasena/{idUsuario}")
    public ResponseEntity<UsuarioAutenticado> cambiarContrasena(@PathVariable Long idUsuario, @RequestBody CambioContrasenaDto usuarioDto) {
        manejadorServicioUsuario.cambiarContrasena(idUsuario, usuarioDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/estado/{idUsuario}")
    public ResponseEntity<CambioEstadoDto> cambiarEstado(@PathVariable Long idUsuario, @RequestBody CambioEstadoDto estadoDto) {
        CambioEstadoDto nuevoEstado = manejadorServicioUsuario.cambiarEstado(idUsuario, estadoDto.getActivo(), estadoDto.getIdentificacion());
        return new ResponseEntity<>(nuevoEstado, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{idUsuario}")
    public Boolean desactivarUsuario(@PathVariable Long idUsuario) {
        return manejadorServicioUsuario.adminDesactivaUsuario(idUsuario);
    }

}
