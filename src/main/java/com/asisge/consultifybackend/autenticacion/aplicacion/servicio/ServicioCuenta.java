package com.asisge.consultifybackend.autenticacion.aplicacion.servicio;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.ActualizarMisDatosDto;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.CambioContrasenaDto;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.CambioCorreoDto;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.MisDatos;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;

public interface ServicioCuenta {

    MisDatos buscarPorIdUsuario(Long idUsuario);

    void cambiarContrasena(Long idUsuario, CambioContrasenaDto usuarioDto);

    UsuarioAutenticado cambiarCorreoElectronico(Long idUsuario, CambioCorreoDto usuarioDto);

    Boolean desactivarMiUsuario(Long idUsuario);

    MisDatos editarMiInformacionBasica(Long idUsuario, ActualizarMisDatosDto editarUsuario);
}
