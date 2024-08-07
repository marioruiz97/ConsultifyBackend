package com.asisge.consultifybackend.actividades.infraestructura.adaptador.convertidor;

import com.asisge.consultifybackend.actividades.dominio.modelo.Actividad;
import com.asisge.consultifybackend.actividades.dominio.modelo.TipoActividad;
import com.asisge.consultifybackend.actividades.infraestructura.adaptador.entidad.EntidadActividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.convertidor.ConvertidorProyecto;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadProyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.convertidor.ConvertidorUsuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.validation.Valid;

public final class ConvertidorActividad {

    private ConvertidorActividad() {
    }

    public static Actividad aDominio(@Valid EntidadActividad entidad) {
        Actividad actividad = null;
        if (entidad != null) {

            Proyecto proyecto = ConvertidorProyecto.construirProyecto(entidad);
            Usuario responsable = ConvertidorUsuario.aUsuarioDominio(entidad.getResponsable());
            TipoActividad tipoActividad = ConvertidorTipoActividad.aDominio(entidad.getTipoActividad());

            actividad = new Actividad(
                    entidad.getId(),
                    entidad.getNombre(),
                    entidad.getDescripcion(),
                    proyecto,
                    entidad.getEstado(),
                    entidad.getFechaCierreEsperado(),
                    tipoActividad,
                    responsable,
                    entidad.getFechaCompletada()
            );
        }
        return actividad;
    }

    public static EntidadActividad aEntidad(Actividad actividad) {
        EntidadActividad entidad = new EntidadActividad();
        entidad.setId(actividad.getId());
        entidad.setNombre(actividad.getNombre());
        entidad.setDescripcion(actividad.getDescripcion());
        entidad.setProyecto(new EntidadProyecto(actividad.getProyecto().getIdProyecto()));
        entidad.setEstado(actividad.getEstado());
        entidad.setFechaCierreEsperado(actividad.getFechaCierreEsperado());
        entidad.setTipoActividad(ConvertidorTipoActividad.aEntidad(actividad.getTipoActividad()));
        entidad.setResponsable(new EntidadUsuario(actividad.getResponsable().getIdUsuario()));

        return entidad;
    }

    public static EntidadActividad aActualizarEntidad(EntidadActividad entidad, Actividad actividad) {
        if (entidad != null) {
            entidad.setNombre(actividad.getNombre());
            entidad.setDescripcion(actividad.getDescripcion());
            entidad.setEstado(actividad.getEstado());
            entidad.setFechaCierreEsperado(actividad.getFechaCierreEsperado());
            entidad.setTipoActividad(ConvertidorTipoActividad.aEntidad(actividad.getTipoActividad()));
            entidad.setResponsable(new EntidadUsuario(actividad.getResponsable().getIdUsuario()));
        }
        return entidad;
    }
}
