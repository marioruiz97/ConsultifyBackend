package com.asisge.consultifybackend.autenticacion.dominio.puerto;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

public interface RepositorioAutorizacion {
    UsuarioAutenticado buscarPorCorreo(String correo);

    UsuarioAutenticado buscarPorNombreUsuarioOCorreo(String usuarioOCorreo);

    UsuarioAutenticado buscarPorIdUsuario(Long idUsuario);

    UsuarioAutenticado editarCorreo(Usuario existente);

    void guardarDatosUsuario(UsuarioAutenticado usuarioAutenticado);

    UsuarioAutenticado buscarPorIdUsuarioAndCorreo(Long idUsuario, String correo);

    void actualizarUltimoInicioSesion(UsuarioAutenticado usuario);
}
