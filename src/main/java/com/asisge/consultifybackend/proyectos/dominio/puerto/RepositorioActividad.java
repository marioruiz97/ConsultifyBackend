package com.asisge.consultifybackend.proyectos.dominio.puerto;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;

import java.util.List;

public interface RepositorioActividad {

    List<Actividad> obtenerActividadesPorProyecto(Proyecto proyecto);

    Actividad crearActividad(Actividad actividad);

    Actividad editarActividad(Long idActividad, Actividad actividad);

    void eliminarActividad(Long idActividad);
}
