package com.asisge.consultifybackend.notificaciones.infraestructura.adaptador.convertidor;

import com.asisge.consultifybackend.notificaciones.dominio.modelo.Notificacion;
import com.asisge.consultifybackend.notificaciones.infraestructura.adaptador.entidad.EntidadNotificacion;

public final class ConvertidorNotificacion {

    private ConvertidorNotificacion() {
    }

    public static Notificacion aDominio(EntidadNotificacion entidad) {
        Notificacion notificacion = null;
        if (entidad != null) {
            notificacion = new Notificacion(
                    entidad.getId(),
                    entidad.getTipoNotificacion(),
                    entidad.getMensaje(),
                    entidad.getIdUsuario(),
                    entidad.getIdProyecto(),
                    entidad.getCreadoEn(),
                    entidad.isLeida()
            );
        }

        return notificacion;
    }

    public static EntidadNotificacion aEntidad(Notificacion notificacion) {
        EntidadNotificacion entidad = new EntidadNotificacion();

        if (notificacion != null) {
            entidad.setId(notificacion.getId());
            entidad.setTipoNotificacion(notificacion.getTipoNotificacion());
            entidad.setMensaje(notificacion.getMensaje());
            entidad.setIdUsuario(notificacion.getIdUsuario());
            entidad.setIdProyecto(notificacion.getIdProyecto());
            entidad.setLeida(Boolean.TRUE);
        }

        return entidad;
    }

}
