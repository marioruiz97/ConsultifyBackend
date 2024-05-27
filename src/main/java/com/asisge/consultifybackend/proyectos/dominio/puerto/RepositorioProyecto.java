package com.asisge.consultifybackend.proyectos.dominio.puerto;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;

import java.util.List;

public interface RepositorioProyecto {
    List<Proyecto> obtenerTodos();

    Proyecto obtenerProyectoPorId(Long idProyecto);

    List<Proyecto> obtenerMisProyectos(Long idUsuario);

    Proyecto crearProyecto(Proyecto proyecto);

    Proyecto editarProyecto(Long idProyecto, Proyecto proyecto);

    boolean existeProyectoPorId(Long idProyecto);

    void eliminarProyecto(Long idProyecto);

    boolean esMiembroProyecto(String correoOUsername);
}
