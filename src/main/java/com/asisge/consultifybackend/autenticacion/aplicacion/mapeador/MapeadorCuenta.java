package com.asisge.consultifybackend.autenticacion.aplicacion.mapeador;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.ActualizarMisDatosDto;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.MisDatos;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MapeadorCuenta {


    public MisDatos aMisDatos(UsuarioAutenticado cuenta) {
        Usuario usuario = cuenta.getUsuario();
        return new MisDatos(
                usuario.getIdentificacion(),
                usuario.getTipoDocumento(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getTelefono(),
                usuario.getCorreo(),
                cuenta.getNombreUsuario(),
                cuenta.getCreadoPor(),
                cuenta.getRol()
        );
    }

    public UsuarioAutenticado aUsuarioAutenticado(UsuarioAutenticado existente, @NotNull ActualizarMisDatosDto editarUsuario) {
        Usuario infoUsuario = existente.getUsuario();
        infoUsuario = new Usuario(
                infoUsuario.getIdUsuario(),
                infoUsuario.getIdentificacion(),
                infoUsuario.getTipoDocumento(),
                editarUsuario.getNombres(),
                editarUsuario.getApellidos(),
                editarUsuario.getTelefono(),
                editarUsuario.getCorreo()
        );
        return new UsuarioAutenticado(
                infoUsuario,
                editarUsuario.getNombreUsuario(),
                existente.getContrasena(),
                existente.getCreadoPor(),
                existente.getUltimoInicio(),
                existente.getActivo(),
                existente.getVerificado(),
                existente.getRol()
        );
    }
}
