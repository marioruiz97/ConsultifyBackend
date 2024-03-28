package com.asisge.consultifybackend.aplicacion.servicio;

import com.asisge.consultifybackend.aplicacion.dto.*;
import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;

import java.util.List;

public interface ServicioUsuario {

    List<Usuario> buscarTodos();

    List<UsuarioAutenticado> buscarTodosAutenticados();

    UsuarioAutenticado buscarUsuarioPorIdentificacion(String identificacion);

    UsuarioAutenticado buscarUsuarioPorCorreo(String correo);

    UsuarioAutenticado crearUsuarioAutenticado(NuevoUsuarioAutenticadoDto usuarioAutenticado);

    UsuarioAutenticado editarInformacionBasica(Long idUsuario, UsuarioBasicoDto editarUsuario);

    void eliminarUsuario(String identificacion);

    void cambiarContrasena(Long idUsuario, CambioContrasenaDto usuarioAutenticado);

    UsuarioAutenticado cambiarCorreoElectronico(Long idUsuario, CambioCorreoDto usuarioDto);

    CambioEstadoDto cambiarEstado(Long idUsuario, boolean activo, String identificacion);
}
