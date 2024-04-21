package com.asisge.consultifybackend.autenticacion.dominio.puerto;

import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

public interface RepositorioAutorizacion {
    UsuarioAutenticado buscarPorCorreo(String correo);

    UsuarioAutenticado buscarPorNombreUsuarioOCorreo(String usuarioOCorreo);

    UsuarioAutenticado buscarPorIdUsuario(Long idUsuario);
}