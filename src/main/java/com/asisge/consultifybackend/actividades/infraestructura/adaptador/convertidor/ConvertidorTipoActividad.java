package com.asisge.consultifybackend.actividades.infraestructura.adaptador.convertidor;

import com.asisge.consultifybackend.actividades.dominio.modelo.TipoActividad;
import com.asisge.consultifybackend.actividades.infraestructura.adaptador.entidad.EntidadTipoActividad;

public final class ConvertidorTipoActividad {

    private ConvertidorTipoActividad() {
    }


    public static TipoActividad aDominio(EntidadTipoActividad entidad) {
        TipoActividad tipoActividad = null;

        if (entidad != null) {
            tipoActividad = new TipoActividad(entidad.getIdTipo(), entidad.getNombre());
        }

        return tipoActividad;
    }


    public static EntidadTipoActividad aEntidad(TipoActividad dominio) {
        EntidadTipoActividad entidad = null;

        if (dominio != null) {
            entidad = new EntidadTipoActividad();
            entidad.setIdTipo(dominio.getIdTipo());
            entidad.setNombre(dominio.getNombre());
        }

        return entidad;
    }

}
