package com.asisge.consultifybackend.actividades.dominio.puerto;

import com.asisge.consultifybackend.actividades.dominio.modelo.Actividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;

import java.util.List;

public interface RepositorioActividad {

    Actividad obtenerActividadPorId(Long idActividad);

    List<Actividad> obtenerActividadesPorProyecto(Proyecto proyecto);

    Actividad crearActividad(Actividad actividad);

    Actividad editarActividad(Long idActividad, Actividad actividad);

    void eliminarActividad(Long idActividad);

    List<Actividad> obtenerMisActividades(String usernameOCorreo);

    boolean esResponsableActividad(Long idActividad, String username);

}
