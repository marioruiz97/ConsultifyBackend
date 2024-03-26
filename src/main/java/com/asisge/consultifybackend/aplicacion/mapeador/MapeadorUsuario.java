package com.asisge.consultifybackend.aplicacion.mapeador;

import com.asisge.consultifybackend.aplicacion.dto.NuevoUsuarioAutenticadoDto;
import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MapeadorUsuario {


    private static final String DUMMY_PASSWORD = "DUMMYpass1*";

    public UsuarioAutenticado aUsuarioAutenticadoDto(UsuarioAutenticado auth) {
        if (auth != null) auth.limpiarContrasena();
        return auth;
    }

    public UsuarioAutenticado aNuevoUsuarioAutenticado(NuevoUsuarioAutenticadoDto nuevoUsuario) {
        UsuarioAutenticado usuarioAutenticado = null;
        if (nuevoUsuario != null) {
            final Usuario usuario = new Usuario(nuevoUsuario.getIdentificacion(), nuevoUsuario.getNombres(), nuevoUsuario.getApellidos(),
                    nuevoUsuario.getTelefono(), nuevoUsuario.getCorreo());
            usuarioAutenticado = new UsuarioAutenticado(usuario, nuevoUsuario.getNombreUsuario(), DUMMY_PASSWORD, LocalDateTime.now(),
                    nuevoUsuario.getCreadoPor(), LocalDateTime.now(), Boolean.FALSE, Boolean.FALSE);
        }
        return usuarioAutenticado;
    }

}
