package com.asisge.consultifybackend.infraestructura.controlador;

import com.asisge.consultifybackend.aplicacion.dto.CambioContrasenaDto;
import com.asisge.consultifybackend.aplicacion.dto.CambioCorreoDto;
import com.asisge.consultifybackend.aplicacion.dto.NuevoUsuarioAutenticadoDto;
import com.asisge.consultifybackend.aplicacion.dto.UsuarioBasicoDto;
import com.asisge.consultifybackend.aplicacion.manejador.ManejadorServicioUsuario;
import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {

    private final ManejadorServicioUsuario manejadorServicioUsuario;

    @Autowired
    public ControladorUsuario(ManejadorServicioUsuario manejadorServicioUsuario) {
        this.manejadorServicioUsuario = manejadorServicioUsuario;
    }

    @GetMapping
    public List<Usuario> buscarUsuarios() {
        return manejadorServicioUsuario.buscarTodos();
    }

    @GetMapping("/autenticados")
    public List<UsuarioAutenticado> buscarUsuariosAutenticados() {
        return manejadorServicioUsuario.buscarTodosAutenticados();
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

}
