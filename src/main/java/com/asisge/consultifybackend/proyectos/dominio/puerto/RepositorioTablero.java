package com.asisge.consultifybackend.proyectos.dominio.puerto;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

import java.util.List;

public interface RepositorioTablero {

    List<UsuarioAutenticado> obtenerPosiblesMiembros(Long idProyecto);

    void agregarMiembroAlProyecto(Long idProyecto, Usuario usuario);

    void quitarMiembroProyecto(Long idProyecto, Long idMiembro);

    List<UsuarioAutenticado> obtenerMiembrosProyecto(Long idProyecto);
}
