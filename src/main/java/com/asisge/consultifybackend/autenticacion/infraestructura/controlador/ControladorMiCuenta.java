package com.asisge.consultifybackend.autenticacion.infraestructura.controlador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioCuenta;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.MisDatos;
import com.asisge.consultifybackend.usuarios.aplicacion.dto.CambioContrasenaDto;
import com.asisge.consultifybackend.usuarios.aplicacion.dto.CambioCorreoDto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuenta")
public class ControladorMiCuenta {

    private final ServicioCuenta manejadorCuenta;

    @Autowired
    public ControladorMiCuenta(ServicioCuenta manejadorCuenta) {
        this.manejadorCuenta = manejadorCuenta;
    }

    @GetMapping("/{idUsuario}")
    public MisDatos cargarMisDatos(@PathVariable Long idUsuario) {
        return manejadorCuenta.buscarPorIdUsuario(idUsuario);
    }

    @PatchMapping("/correo/{idUsuario}")
    public ResponseEntity<UsuarioAutenticado> cambiarCorreoElectronico(@PathVariable Long idUsuario, @RequestBody CambioCorreoDto usuarioDto) {
        usuarioDto.setIdUsuario(idUsuario);
        return new ResponseEntity<>(manejadorCuenta.cambiarCorreoElectronico(idUsuario, usuarioDto), HttpStatus.OK);
    }

    @PatchMapping("/contrasena/{idUsuario}")
    public ResponseEntity<UsuarioAutenticado> cambiarContrasena(@PathVariable Long idUsuario, @RequestBody CambioContrasenaDto usuarioDto) {
        usuarioDto.setIdUsuario(idUsuario);
        manejadorCuenta.cambiarContrasena(idUsuario, usuarioDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{idUsuario}")
    public Boolean desactivarUsuario(@PathVariable Long idUsuario) {
        return manejadorCuenta.desactivarMiUsuario(idUsuario);
    }



}
