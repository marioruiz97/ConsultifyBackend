package com.asisge.consultifybackend.dominio.puerto;

import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;

import java.util.List;

public interface RepositorioUsuario {

    List<Usuario> buscarTodos();

    List<UsuarioAutenticado> buscarTodosUsuariosAutenticados();

    Usuario buscarUsuarioPorId(Long idUsuario);

    UsuarioAutenticado buscarUsuarioPorIdentificacion(String identificacion);

    UsuarioAutenticado buscarUsuarioPorCorreo(String correo);

    UsuarioAutenticado buscarPorCorreoOUsername(String correoOUsername);

    UsuarioAutenticado crearUsuarioAutenticado(UsuarioAutenticado usuarioAutenticado);

    void eliminarUsuario(String identificacion);

    void cambiarContrasena(UsuarioAutenticado usuarioAutenticado);

    UsuarioAutenticado editarInformacionBasica(Usuario aGuardar);

    UsuarioAutenticado editarCorreo(Usuario existente);

    UsuarioAutenticado cambiarEstado(UsuarioAutenticado usuario, boolean activo);
}
