package com.asisge.consultifybackend.autenticacion.infraestructura.controlador;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.AuthenticationRequest;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.AuthenticationResponse;
import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("permitAll()")
@RequestMapping("/auth")
public class ControladorAutorizacion {

    public static final String TOKEN = "token";

    private final ServicioAutenticacion servicioAutenticacion;

    @Autowired
    public ControladorAutorizacion(ServicioAutenticacion servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authRequest) {
        AuthenticationResponse jwtDto = servicioAutenticacion.iniciarSesion(authRequest);
        return ResponseEntity.ok(jwtDto);
    }


    @PostMapping("/recuperar")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void recuperarContrasena(@RequestBody ModelMap model) {

        String correo = model.get("correo").toString();
        servicioAutenticacion.recuperarContrasena(correo);
    }


    @PostMapping("/reiniciar-contrasena")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void reiniciarContrasena(@RequestBody ModelMap model) {

        @Valid String contrasena = model.get("contrasena").toString();
        @Valid String token = model.get(TOKEN).toString();
        servicioAutenticacion.reiniciarClave(token, contrasena);
    }


    @PostMapping("/verificar-cuenta/{idUsuario}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void verificarNuevaCuenta(@RequestBody ModelMap model, @PathVariable Long idUsuario) {

        @Valid String contrasena = model.get("contrasena").toString();
        @Valid String token = model.get(TOKEN).toString();

        servicioAutenticacion.verificarNuevaCuenta(idUsuario, contrasena, token);
    }

    @PatchMapping("/verificar-cuenta")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void verificarCorreoCuenta(@RequestBody ModelMap model) {

        @Valid String token = model.get(TOKEN).toString();
        servicioAutenticacion.verificarCorreoCuenta(token);
    }

}
