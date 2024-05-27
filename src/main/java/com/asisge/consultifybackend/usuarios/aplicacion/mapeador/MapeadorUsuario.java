package com.asisge.consultifybackend.usuarios.aplicacion.mapeador;

import com.asisge.consultifybackend.usuarios.aplicacion.dto.NuevoUsuarioAutenticadoDto;
import com.asisge.consultifybackend.usuarios.aplicacion.dto.UsuarioListaDto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import org.springframework.stereotype.Component;

@Component
public class MapeadorUsuario {


    private static final String DUMMY_PASSWORD = "DUMMYpass1*";

    public UsuarioListaDto aUsuarioLista(UsuarioAutenticado autenticado) {
        Usuario usuario = autenticado.getUsuario();
        return new UsuarioListaDto(
                usuario.getIdUsuario(),
                usuario.getIdentificacion(),
                usuario.getTipoDocumento(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getTelefono(),
                autenticado.getNombreUsuario(),
                usuario.getCorreo(),
                autenticado.getActivo(),
                autenticado.getRol(),
                autenticado.getCreadoPor(),
                autenticado.getUltimoInicio());
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
                    nuevoUsuario.getCreadoPor(),
                    null,
                    Boolean.TRUE,
                    Boolean.TRUE,
                    nuevoUsuario.getRol());
            // TODO cambiar a Boolean.FALSE cuando se envie correo electronico
        }
        return usuarioAutenticado;
    }


    public UsuarioAutenticado aEditarUsuarioAutenticado(UsuarioAutenticado existente, NuevoUsuarioAutenticadoDto editarUsuario) {
        UsuarioAutenticado usuario = null;
        if (editarUsuario != null) {
            final Usuario infoUsuario = new Usuario(
                    existente.getUsuario().getIdUsuario(),
                    editarUsuario.getIdentificacion(),
                    editarUsuario.getTipoDocumento(),
                    editarUsuario.getNombres(),
                    editarUsuario.getApellidos(),
                    editarUsuario.getTelefono(),
                    editarUsuario.getCorreo()
            );
            usuario = new UsuarioAutenticado(
                    infoUsuario,
                    editarUsuario.getNombreUsuario(),
                    existente.getContrasena(),
                    existente.getCreadoPor(),
                    existente.getUltimoInicio(),
                    existente.getActivo(),
                    existente.getVerificado(),
                    editarUsuario.getRol()
            );
        }
        return usuario;
    }
}
