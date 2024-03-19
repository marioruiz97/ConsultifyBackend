package com.asisge.consultifybackend.aplicacion.servicio;

import com.asisge.consultifybackend.aplicacion.dto.CambioContrasenaDto;
import com.asisge.consultifybackend.aplicacion.dto.NuevoUsuarioAutenticadoDto;
import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;

import java.util.List;

public interface ServicioUsuario {

    UsuarioAutenticado crearUsuarioAutenticado(NuevoUsuarioAutenticadoDto usuarioAutenticado);

    List<Usuario> buscarTodos();

    List<UsuarioAutenticado> buscarTodosAutenticados();

    UsuarioAutenticado buscarUsuarioPorIdentificacion(String identificacion);

    UsuarioAutenticado buscarUsuarioPorCorreo(String correo);

    void eliminarUsuario(String identificacion);

    void cambiarContrasena(CambioContrasenaDto usuarioAutenticado);

}
