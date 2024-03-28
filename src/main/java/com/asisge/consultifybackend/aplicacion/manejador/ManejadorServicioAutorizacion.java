package com.asisge.consultifybackend.aplicacion.manejador;

import com.asisge.consultifybackend.aplicacion.servicio.ServicioAutorizacion;
import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.puerto.RepositorioAutorizacion;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManejadorServicioAutorizacion implements ServicioAutorizacion {

    private final RepositorioAutorizacion repositorioAutorizacion;

    @Autowired
    public ManejadorServicioAutorizacion(RepositorioAutorizacion repositorioAutorizacion) {
        this.repositorioAutorizacion = repositorioAutorizacion;
    }

    @Override
    public void recuperarContrasena(String correo) {
        Usuario usuario = buscarUsuarioPorCorreo(correo);
        /*
        VerificationToken token = service.validVerificationToken(usuario);
        emailService.sendRecoveryPassword(token);
         */
    }

    private Usuario buscarUsuarioPorCorreo(String correo) {
        Usuario usuario = repositorioAutorizacion.buscarPorCorreo(correo);
        if (usuario == null)
            throw new EntityNotFoundException("No se encontro el correo electronico en la base de datos");
        return usuario;
    }
}
