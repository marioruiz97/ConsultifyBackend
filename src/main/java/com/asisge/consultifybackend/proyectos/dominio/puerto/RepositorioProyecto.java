package com.asisge.consultifybackend.proyectos.dominio.puerto;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

import java.util.List;

public interface RepositorioProyecto {
    List<Proyecto> obtenerTodos();

    Proyecto obtenerProyectoPorId(Long idProyecto);

    List<Proyecto> obtenerMisProyectos(Long idUsuario);

    List<UsuarioAutenticado> obtenerPosiblesMiembros(Long idProyecto);

    Proyecto crearProyecto(Proyecto proyecto);

    Proyecto editarProyecto(Long idProyecto, Proyecto proyecto);

    boolean existeProyectoPorId(Long idProyecto);

    void eliminarProyecto(Long idProyecto);

}
