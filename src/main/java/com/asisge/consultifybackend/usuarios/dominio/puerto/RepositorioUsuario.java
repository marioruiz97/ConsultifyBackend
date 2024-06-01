package com.asisge.consultifybackend.usuarios.dominio.puerto;

import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

import java.util.List;

public interface RepositorioUsuario {

    List<UsuarioAutenticado> buscarTodosUsuariosAutenticados();

    UsuarioAutenticado buscarUsuarioPorIdUsuario(Long idUsuario);

    UsuarioAutenticado buscarPorCorreoOUsername(String correoOUsername);

    UsuarioAutenticado crearUsuarioAutenticado(UsuarioAutenticado usuarioAutenticado);

    UsuarioAutenticado editarInformacionUsuario(UsuarioAutenticado aGuardar);

    UsuarioAutenticado cambiarEstado(UsuarioAutenticado usuario, boolean activo);

    List<UsuarioAutenticado> asesorBuscarTodosUsuariosAutenticados();

}
