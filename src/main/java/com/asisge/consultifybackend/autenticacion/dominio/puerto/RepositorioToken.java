package com.asisge.consultifybackend.autenticacion.dominio.puerto;

import com.asisge.consultifybackend.autenticacion.dominio.modelo.TokenVerificacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

public interface RepositorioToken {

    TokenVerificacion crearTokenVerificacion(UsuarioAutenticado usuario);

    TokenVerificacion obtenerToken(String token);

    void eliminarToken(TokenVerificacion tokenVerificacion);

}
