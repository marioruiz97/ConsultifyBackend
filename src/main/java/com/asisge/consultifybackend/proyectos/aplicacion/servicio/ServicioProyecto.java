package com.asisge.consultifybackend.proyectos.aplicacion.servicio;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;

import java.util.List;

public interface ServicioProyecto {
    List<Proyecto> obtenerTodos();

    Proyecto obtenerProyectoPorId(Long idProyecto);

    Proyecto crearProyecto(Proyecto proyecto);

    Proyecto editarProyecto(Long idProyecto, Proyecto proyecto);
}
