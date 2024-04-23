package com.asisge.consultifybackend.usuarios.aplicacion.servicio;

import com.asisge.consultifybackend.usuarios.aplicacion.dto.*;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

import java.util.List;

public interface ServicioUsuario {

    List<UsuarioListaDto> buscarTodos();

    UsuarioAutenticado buscarUsuarioPorId(Long idUsuario);

    UsuarioAutenticado crearUsuarioAutenticado(NuevoUsuarioAutenticadoDto usuarioAutenticado);

    UsuarioAutenticado editarInformacionBasica(Long idUsuario, NuevoUsuarioAutenticadoDto editarUsuario);

    void cambiarContrasena(Long idUsuario, CambioContrasenaDto usuarioAutenticado);

    UsuarioAutenticado cambiarCorreoElectronico(Long idUsuario, CambioCorreoDto usuarioDto);

    CambioEstadoDto cambiarEstado(Long idUsuario, boolean activo, String identificacion);

    Boolean adminDesactivaUsuario(Long idUsuario);

}
