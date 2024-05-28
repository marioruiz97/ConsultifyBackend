package com.asisge.consultifybackend.utilidad.aplicacion.servicio;

import com.asisge.consultifybackend.autenticacion.dominio.modelo.TokenVerificacion;
import org.springframework.scheduling.annotation.Async;


public interface ServicioCorreo {

    @Async
    void enviarCorreo(String to, String subject, String text);

    @Async
    void enviarCorreoRecuperacion(String to, String subject, TokenVerificacion token);

    @Async
    void enviarCorreoVerificacion(String to, String subject, TokenVerificacion token);

}
