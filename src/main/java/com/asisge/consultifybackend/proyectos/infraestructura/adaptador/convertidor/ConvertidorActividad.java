package com.asisge.consultifybackend.proyectos.infraestructura.adaptador.convertidor;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Seguimiento;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadActividad;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadProyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
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
            Proyecto proyecto = ConvertidorProyecto.aDominio(entidad.getProyecto());
            UsuarioAutenticado responsable = ConvertidorUsuario.aDominio(entidad.getResponsable(), entidad.getResponsable().getCreadoPor().toString());
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
        entidad.setResponsable(new EntidadUsuario(actividad.getResponsable().getUsuario().getIdUsuario()));

        return entidad;
    }
}
