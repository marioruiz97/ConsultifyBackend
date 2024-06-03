package com.asisge.consultifybackend.autenticacion.aplicacion.servicio;

import com.asisge.consultifybackend.autenticacion.dominio.modelo.TokenVerificacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

public interface ServicioToken {

    TokenVerificacion crearTokenVerificacion(UsuarioAutenticado usuario);

    TokenVerificacion obtenerToken(String token);

    void validarToken(TokenVerificacion token);

    void eliminarToken(TokenVerificacion tokenVerificacion);


}