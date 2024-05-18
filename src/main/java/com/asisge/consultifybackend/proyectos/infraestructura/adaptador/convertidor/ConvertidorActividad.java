package com.asisge.consultifybackend.proyectos.infraestructura.adaptador.convertidor;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Seguimiento;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadActividad;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadProyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.convertidor.ConvertidorUsuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

public final class ConvertidorActividad {

    private ConvertidorActividad() {
    }

    public static Actividad aDominio(@Valid EntidadActividad entidad) {
        Actividad actividad = null;
        if (entidad != null) {
            List<Seguimiento> seguimientos = new ArrayList<>();
            Proyecto proyecto = new Proyecto(entidad.getProyecto().getIdProyecto());
            Usuario responsable = ConvertidorUsuario.aUsuarioDominio(entidad.getResponsable());
            actividad = new Actividad(
                    entidad.getId(),
                    entidad.getNombre(),
                    entidad.getDescripcion(),
                    proyecto,
                    entidad.getEstado(),
                    entidad.getFechaCierreEsperado(),
                    responsable,
                    seguimientos
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
        entidad.setResponsable(new EntidadUsuario(actividad.getResponsable().getIdUsuario()));

        return entidad;
    }

    public static EntidadActividad aActualizarEntidad(EntidadActividad entidad, Actividad actividad) {
        if (entidad != null) {
            entidad.setNombre(actividad.getNombre());
            entidad.setDescripcion(actividad.getDescripcion());
            entidad.setEstado(actividad.getEstado());
            entidad.setFechaCierreEsperado(actividad.getFechaCierreEsperado());
            entidad.setResponsable(new EntidadUsuario(actividad.getResponsable().getIdUsuario()));
        }
        return entidad;
    }
}
