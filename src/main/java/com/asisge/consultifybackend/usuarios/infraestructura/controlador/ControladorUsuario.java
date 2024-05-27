package com.asisge.consultifybackend.usuarios.infraestructura.controlador;


import com.asisge.consultifybackend.usuarios.aplicacion.dto.CambioEstadoDto;
import com.asisge.consultifybackend.usuarios.aplicacion.dto.NuevoUsuarioAutenticadoDto;
import com.asisge.consultifybackend.usuarios.aplicacion.dto.UsuarioListaDto;
import com.asisge.consultifybackend.usuarios.aplicacion.servicio.ServicioUsuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/usuarios")
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

    @GetMapping("/{idUsuario}")
    public UsuarioAutenticado obternerPorId(@PathVariable Long idUsuario) {
        return manejadorServicioUsuario.buscarUsuarioPorId(idUsuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioAutenticado> crearUsuario(@RequestBody NuevoUsuarioAutenticadoDto nuevoUsuario) {
        return new ResponseEntity<>(manejadorServicioUsuario.crearUsuarioAutenticado(nuevoUsuario), HttpStatus.CREATED);
    }

    @PatchMapping("/{idUsuario}")
    public ResponseEntity<UsuarioAutenticado> editarInformacionBasica(@PathVariable Long idUsuario, @RequestBody NuevoUsuarioAutenticadoDto editarUsuario) {
        return new ResponseEntity<>(manejadorServicioUsuario.editarInformacionBasica(idUsuario, editarUsuario), HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/estado/{idUsuario}")
    public ResponseEntity<Boolean> cambiarEstado(@PathVariable Long idUsuario, @RequestBody CambioEstadoDto estadoDto) {
        Boolean nuevoEstado = manejadorServicioUsuario.cambiarEstado(idUsuario, estadoDto.getActivo());
        return ResponseEntity.ok(nuevoEstado);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{idUsuario}")
    public Boolean desactivarUsuario(@PathVariable Long idUsuario) {
        return manejadorServicioUsuario.adminDesactivaUsuario(idUsuario);
    }

}
