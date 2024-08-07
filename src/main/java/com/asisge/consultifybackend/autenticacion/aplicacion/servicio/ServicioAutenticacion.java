package com.asisge.consultifybackend.autenticacion.aplicacion.servicio;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.AuthenticationRequest;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.AuthenticationResponse;

public interface ServicioAutenticacion {

    void recuperarContrasena(String correo);

    AuthenticationResponse iniciarSesion(AuthenticationRequest authRequest);

    String obtenerNombreUsuarioEnSesion();

    boolean estaAutenticado();

    void reiniciarClave(String token, String contrasena);

    void verificarCorreoCuenta(String token);

    void verificarNuevaCuenta(Long idUsuario, String contrasena, String token);

}
