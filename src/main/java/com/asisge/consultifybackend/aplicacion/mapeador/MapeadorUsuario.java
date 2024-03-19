package com.asisge.consultifybackend.aplicacion.mapeador;

import com.asisge.consultifybackend.aplicacion.dto.CambioContrasenaDto;
import com.asisge.consultifybackend.aplicacion.dto.NuevoUsuarioAutenticadoDto;
import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;
import org.springframework.stereotype.Component;

@Component
public class MapeadorUsuario {


    public UsuarioAutenticado aUsuarioAutenticadoDto(UsuarioAutenticado auth) {
        if (auth != null) auth.limpiarContrasena();
        return auth;
    }

    public UsuarioAutenticado aUsuarioAutenticado(NuevoUsuarioAutenticadoDto nuevoUsuario) {
        UsuarioAutenticado usuarioAutenticado = null;
        if (nuevoUsuario != null) {
            final Usuario usuario = new Usuario(nuevoUsuario.getIdUsuario(), nuevoUsuario.getIdentificacion(), nuevoUsuario.getNombres(), nuevoUsuario.getApellidos(),
                    nuevoUsuario.getTelefono(), nuevoUsuario.getCorreo());
            usuarioAutenticado = new UsuarioAutenticado(usuario, nuevoUsuario.getNombreUsuario(), nuevoUsuario.getContrasena(), nuevoUsuario.getCreadoEn(),
                    nuevoUsuario.getCreadoPor(), nuevoUsuario.getUltimoInicio(), nuevoUsuario.getActivo(), nuevoUsuario.getVerificado());
        }
        return usuarioAutenticado;
    }

    public UsuarioAutenticado cambioContrasena(CambioContrasenaDto usuarioAutenticado) {
        return null;
    }
}
