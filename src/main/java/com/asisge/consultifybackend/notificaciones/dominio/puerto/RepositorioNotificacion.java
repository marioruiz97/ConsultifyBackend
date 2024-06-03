package com.asisge.consultifybackend.notificaciones.dominio.puerto;

import com.asisge.consultifybackend.notificaciones.dominio.modelo.Notificacion;

import java.util.List;

public interface RepositorioNotificacion {
    List<Notificacion> obtenerNotificacionesUsuario(Long idUsuario);

    List<Notificacion> obtenerNotificacionesProyecto(Long idProyecto);

    void eliminarNotificacion(Long idNotificacion);

    void crearNotificacion(Notificacion notificacion);

}
