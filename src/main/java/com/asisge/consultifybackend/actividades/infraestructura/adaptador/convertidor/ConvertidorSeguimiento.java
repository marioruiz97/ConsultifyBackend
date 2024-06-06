package com.asisge.consultifybackend.actividades.infraestructura.adaptador.convertidor;

import com.asisge.consultifybackend.actividades.dominio.modelo.Actividad;
import com.asisge.consultifybackend.actividades.dominio.modelo.Seguimiento;
import com.asisge.consultifybackend.actividades.infraestructura.adaptador.entidad.EntidadActividad;
import com.asisge.consultifybackend.actividades.infraestructura.adaptador.entidad.EntidadSeguimiento;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.convertidor.ConvertidorUsuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;

public final class ConvertidorSeguimiento {

    private ConvertidorSeguimiento() {
    }


    public static Seguimiento aDominio(EntidadSeguimiento entidad) {
        Seguimiento seguimiento = null;
        if (entidad != null) {
            seguimiento = new Seguimiento(
                    entidad.getIdSeguimiento(),
                    new Actividad(entidad.getActividad().getId()),
                    ConvertidorUsuario.aUsuarioDominio(entidad.getUsuario()),
                    entidad.getFechaSeguimiento(),
                    entidad.getComentarios()
            );
        }
        return seguimiento;
    }

    public static EntidadSeguimiento aEntidad(Seguimiento seguimiento) {
        EntidadSeguimiento entidad = new EntidadSeguimiento();
        entidad.setIdSeguimiento(seguimiento.getIdSeguimiento());
        entidad.setActividad(new EntidadActividad(seguimiento.getActividad().getId()));
        entidad.setUsuario(new EntidadUsuario(seguimiento.getUsuario().getIdUsuario()));
        entidad.setFechaSeguimiento(seguimiento.getFechaSeguimiento());
        entidad.setComentarios(seguimiento.getComentarios());

        return entidad;
    }
}
