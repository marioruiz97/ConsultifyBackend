package com.asisge.consultifybackend.autenticacion.aplicacion.manejador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioToken;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.TokenVerificacion;
import com.asisge.consultifybackend.autenticacion.dominio.puerto.RepositorioToken;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.utilidad.dominio.excepcion.ViolacionIntegridadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManejadorServicioToken implements ServicioToken {

    private final RepositorioToken repositorioToken;

    @Autowired
    public ManejadorServicioToken(RepositorioToken repositorioToken) {
        this.repositorioToken = repositorioToken;
    }


    @Override
    public TokenVerificacion obtenerToken(String token) {
        return repositorioToken.obtenerToken(token);
    }


    @Override
    public TokenVerificacion crearTokenVerificacion(UsuarioAutenticado usuario) {
        return repositorioToken.crearTokenVerificacion(usuario);
    }


    @Override
    public void validarToken(TokenVerificacion token) {
        if (token.haExpirado()) {
            repositorioToken.eliminarToken(token);
            throw new ViolacionIntegridadException("El token ha expirado");
        }
    }


    @Override
    public void eliminarToken(TokenVerificacion tokenVerificacion) {
        repositorioToken.eliminarToken(tokenVerificacion);
    }


}
