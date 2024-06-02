package com.asisge.consultifybackend.autenticacion.aplicacion.servicio;

import com.asisge.consultifybackend.autenticacion.dominio.modelo.MisDatos;
import org.springframework.scheduling.annotation.Async;

public interface NotificadorCuenta {

    @Async
    void notificarCambioCorreo(Long idUsuario, MisDatos misDatos);

    @Async
    void notificarCambioContrasena(Long idUsuario);

}
