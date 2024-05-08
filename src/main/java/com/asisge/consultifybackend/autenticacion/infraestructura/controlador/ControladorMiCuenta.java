package com.asisge.consultifybackend.autenticacion.infraestructura.controlador;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.ActualizarMisDatosDto;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.CambioContrasenaDto;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.CambioCorreoDto;
import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioCuenta;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.MisDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuenta")
@PreAuthorize("isAuthenticated()")
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

    @PatchMapping("/{idUsuario}")
    public ResponseEntity<MisDatos> editarInformacionBasica(@PathVariable Long idUsuario, @RequestBody ActualizarMisDatosDto editarUsuario) {
        return new ResponseEntity<>(manejadorCuenta.editarMiInformacionBasica(idUsuario, editarUsuario), HttpStatus.CREATED);
    }

    @PatchMapping("/correo/{idUsuario}")
    public ResponseEntity<MisDatos> cambiarCorreoElectronico(@PathVariable Long idUsuario, @RequestBody CambioCorreoDto usuarioDto) {
        usuarioDto.setIdUsuario(idUsuario);
        return new ResponseEntity<>(manejadorCuenta.cambiarCorreoElectronico(idUsuario, usuarioDto), HttpStatus.OK);
    }

    @PatchMapping("/contrasena/{idUsuario}")
    public ResponseEntity<Boolean> cambiarContrasena(@PathVariable Long idUsuario, @RequestBody CambioContrasenaDto usuarioDto) {
        usuarioDto.setIdUsuario(idUsuario);
        manejadorCuenta.cambiarContrasena(idUsuario, usuarioDto);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @DeleteMapping("/{idUsuario}")
    public Boolean desactivarUsuario(@PathVariable Long idUsuario) {
        return manejadorCuenta.desactivarMiUsuario(idUsuario);
    }


}
