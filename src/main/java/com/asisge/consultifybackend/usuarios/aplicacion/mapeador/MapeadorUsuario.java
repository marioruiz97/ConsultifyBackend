package com.asisge.consultifybackend.usuarios.aplicacion.mapeador;

import com.asisge.consultifybackend.usuarios.aplicacion.dto.NuevoUsuarioAutenticadoDto;
import com.asisge.consultifybackend.usuarios.aplicacion.dto.UsuarioBasicoDto;
import com.asisge.consultifybackend.usuarios.aplicacion.dto.UsuarioListaDto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MapeadorUsuario {


    private static final String DUMMY_PASSWORD = "DUMMYpass1*";

    public static UsuarioListaDto aUsuarioLista(UsuarioAutenticado autenticado) {
        Usuario usuario = autenticado.getUsuario();
        return new UsuarioListaDto(
                usuario.getIdUsuario(),
                usuario.getIdentificacion(),
                usuario.getTipoDocumento(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getTelefono(),
                usuario.getCorreo(),
                autenticado.getActivo(),
                autenticado.getRol(),
                autenticado.getCreadoPor());
    }

    public UsuarioAutenticado aUsuarioAutenticadoDto(UsuarioAutenticado auth) {
        if (auth != null) auth.limpiarContrasena();
        return auth;
    }

    public UsuarioAutenticado aNuevoUsuarioAutenticado(NuevoUsuarioAutenticadoDto nuevoUsuario) {
        UsuarioAutenticado usuarioAutenticado = null;
        if (nuevoUsuario != null) {
            final Usuario usuario = new Usuario(
                    nuevoUsuario.getIdentificacion(),
                    nuevoUsuario.getTipoDocumento(),
                    nuevoUsuario.getNombres(),
                    nuevoUsuario.getApellidos(),
                    nuevoUsuario.getTelefono(),
                    nuevoUsuario.getCorreo());

            usuarioAutenticado = new UsuarioAutenticado(
                    usuario,
                    nuevoUsuario.getNombreUsuario(),
                    DUMMY_PASSWORD,
                    LocalDateTime.now(),
                    nuevoUsuario.getCreadoPor(),
                    LocalDateTime.now(),
                    Boolean.TRUE,
                    Boolean.TRUE,
                    nuevoUsuario.getRol());
            // TODO cambiar a Boolean.FALSE cuando se envie correo electronico
        }
        return usuarioAutenticado;
    }

    public Usuario aNuevoUsuario(Usuario existente, UsuarioBasicoDto usuarioDto) {
        return new Usuario(
                existente.getIdUsuario(),
                existente.getIdentificacion(),
                existente.getTipoDocumento(),
                usuarioDto.getNombres(),
                usuarioDto.getApellidos(),
                usuarioDto.getTelefono(),
                existente.getCorreo());
    }
}
