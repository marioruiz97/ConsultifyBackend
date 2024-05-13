package com.asisge.consultifybackend.proyectos.aplicacion.servicio;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.MiembroDto;
import com.asisge.consultifybackend.proyectos.aplicacion.dto.TableroProyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

import java.util.List;

public interface ServicioTablero {
    TableroProyecto obtenerProyectoPorId(Long idProyecto);

    List<UsuarioAutenticado> obtenerPosiblesMiembros(Long idProyecto);

    UsuarioAutenticado agregarMiembroAlProyecto(Long idProyecto, MiembroDto miembroDto);
}
