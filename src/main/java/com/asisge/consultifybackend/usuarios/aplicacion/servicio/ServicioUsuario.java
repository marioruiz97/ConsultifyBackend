package com.asisge.consultifybackend.usuarios.aplicacion.servicio;

import com.asisge.consultifybackend.usuarios.aplicacion.dto.*;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

import java.util.List;

public interface ServicioUsuario {

    List<UsuarioListaDto> buscarTodos();

    UsuarioAutenticado buscarUsuarioPorId(Long idUsuario);

    UsuarioAutenticado crearUsuarioAutenticado(NuevoUsuarioAutenticadoDto usuarioAutenticado);

    UsuarioAutenticado editarInformacionBasica(Long idUsuario, NuevoUsuarioAutenticadoDto editarUsuario);

    boolean cambiarEstado(Long idUsuario, boolean activo);

    Boolean adminDesactivaUsuario(Long idUsuario);

}
