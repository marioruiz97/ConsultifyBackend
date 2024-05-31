package com.asisge.consultifybackend.notificaciones.aplicacion.servicio;

import com.asisge.consultifybackend.notificaciones.dominio.modelo.Notificacion;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface ServicioNotificacion {

    List<Notificacion> obtenerNotificacionesUsuario(Long idUsuario);

    List<Notificacion> obtenerNotificacionesProyecto(Long idProyecto);

    void eliminarNotificacion(Long idNotificacion);

    @Async
    void crearNotificacion(Notificacion notificacion);
}
