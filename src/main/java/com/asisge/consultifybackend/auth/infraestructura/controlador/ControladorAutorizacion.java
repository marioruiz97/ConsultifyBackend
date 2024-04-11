package com.asisge.consultifybackend.auth.infraestructura.controlador;

import com.asisge.consultifybackend.auth.aplicacion.dto.AuthenticationRequest;
import com.asisge.consultifybackend.auth.aplicacion.dto.AuthenticationResponse;
import com.asisge.consultifybackend.auth.aplicacion.manejador.ManejadorServicioAutenticacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ControladorAutorizacion {

    private final ManejadorServicioAutenticacion servicioAutenticacion;

    @Autowired
    public ControladorAutorizacion(ManejadorServicioAutenticacion servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }

    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authRequest) {
        AuthenticationResponse jwtDto = servicioAutenticacion.iniciarSesion(authRequest);
        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("/recuperar")
    public ResponseEntity<Usuario> recuperarContrasena(@RequestBody ModelMap model) {
        String correo = model.get("correo").toString();
        servicioAutenticacion.recuperarContrasena(correo);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
