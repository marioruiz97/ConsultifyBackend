package com.asisge.consultifybackend.dominio.puerto;

import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;

import java.util.List;

public interface RepositorioUsuario {

    UsuarioAutenticado crearUsuarioAutenticado(UsuarioAutenticado usuarioAutenticado);

    List<Usuario> buscarTodos();

    List<UsuarioAutenticado> buscarTodosUsuariosAutenticados();

    UsuarioAutenticado buscarUsuarioPorIdentificacion(String identificacion);

    UsuarioAutenticado buscarUsuarioPorCorreo(String correo);

    void eliminarUsuario(String identificacion);

    void cambiarContrasena(UsuarioAutenticado usuarioAutenticado);


}
