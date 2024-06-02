package com.asisge.consultifybackend.autenticacion.aplicacion.manejador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.NotificadorCuenta;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.MisDatos;
import com.asisge.consultifybackend.notificaciones.aplicacion.servicio.ServicioNotificacion;
import com.asisge.consultifybackend.notificaciones.dominio.modelo.Notificacion;
import com.asisge.consultifybackend.notificaciones.dominio.modelo.TipoNotificacion;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ManejadorNotificadorCuenta implements NotificadorCuenta {

    private final Logger logger = LoggerFactory.getLogger(ManejadorNotificadorCuenta.class);
    private final ServicioNotificacion servicioNotificacion;


    @Autowired
    public ManejadorNotificadorCuenta(ServicioNotificacion servicioNotificacion) {
        this.servicioNotificacion = servicioNotificacion;
    }

    @Async
    @Override
    public void notificarCambioCorreo(Long idUsuario, MisDatos misDatos) {
        try {

            String mensaje = Mensajes.getString("notificacion.cuenta.cambio.correo",
                    LocalDateTime.now(ZoneId.systemDefault()),
                    misDatos.getCorreo());

            Notificacion notificacion = servicioNotificacion.construirNotificacion(idUsuario,
                    null,
                    mensaje,
                    TipoNotificacion.INFO);

            servicioNotificacion.crearNotificacion(notificacion);

        } catch (Exception e) {
            logger.error("Fallo en notificar cuenta", e);
        }
    }


    @Async
    @Override
    public void notificarCambioContrasena(Long idUsuario) {
        try {

            String mensaje = Mensajes.getString("notificacion.cuenta.cambio.contrasena", LocalDateTime.now(ZoneId.systemDefault()));

            Notificacion notificacion = servicioNotificacion.construirNotificacion(idUsuario,
                    null,
                    mensaje,
                    TipoNotificacion.INFO);

            servicioNotificacion.crearNotificacion(notificacion);

        } catch (Exception e) {
            logger.error("Fallo en notificar cuenta", e);
        }
    }
}
