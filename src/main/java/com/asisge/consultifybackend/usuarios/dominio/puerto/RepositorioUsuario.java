package com.asisge.consultifybackend.usuarios.dominio.puerto;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

import java.util.List;

public interface RepositorioUsuario {

    List<UsuarioAutenticado> buscarTodosUsuariosAutenticados();

    Usuario buscarUsuarioPorId(Long idUsuario);

    UsuarioAutenticado buscarUsuarioPorIdUsuario(Long idUsuario);

    UsuarioAutenticado buscarUsuarioPorIdentificacion(String identificacion);

    UsuarioAutenticado buscarPorCorreoOUsername(String correoOUsername);

    UsuarioAutenticado crearUsuarioAutenticado(UsuarioAutenticado usuarioAutenticado);

    void cambiarContrasena(UsuarioAutenticado usuarioAutenticado);

    UsuarioAutenticado editarInformacionUsuario(UsuarioAutenticado aGuardar);

    UsuarioAutenticado editarCorreo(Usuario existente);

    UsuarioAutenticado cambiarEstado(UsuarioAutenticado usuario, boolean activo);

}
