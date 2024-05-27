package com.asisge.consultifybackend.usuarios.aplicacion.manejador;


import com.asisge.consultifybackend.usuarios.aplicacion.dto.NuevoUsuarioAutenticadoDto;
import com.asisge.consultifybackend.usuarios.aplicacion.dto.UsuarioListaDto;
import com.asisge.consultifybackend.usuarios.aplicacion.mapeador.MapeadorUsuario;
import com.asisge.consultifybackend.usuarios.aplicacion.servicio.GeneradorContrasena;
import com.asisge.consultifybackend.usuarios.aplicacion.servicio.ServicioUsuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.dominio.puerto.RepositorioUsuario;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import com.asisge.consultifybackend.utilidad.dominio.modelo.Dto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ManejadorServicioUsuario implements ServicioUsuario {

    public static final String VALIDACION_DATOS_OBLIGATORIOS = "El objeto no pasó la validación de usuario. Verifica los datos obligatorios y el formato de teléfono/correo";
    private final Logger logger = LoggerFactory.getLogger(ManejadorServicioUsuario.class);
    private final RepositorioUsuario repositorioUsuario;
    private final MapeadorUsuario mapeadorUsuario;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ManejadorServicioUsuario(RepositorioUsuario repositorioUsuario, MapeadorUsuario mapeadorUsuario, PasswordEncoder passwordEncoder) {
        this.repositorioUsuario = repositorioUsuario;
        this.mapeadorUsuario = mapeadorUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UsuarioListaDto> buscarTodos() {
        return repositorioUsuario.buscarTodosUsuariosAutenticados().stream().map(mapeadorUsuario::aUsuarioLista).toList();
    }

    @Override
    public UsuarioAutenticado buscarUsuarioPorId(Long idUsuario) {
        return devolverUsuarioSinClave(repositorioUsuario.buscarUsuarioPorIdUsuario(idUsuario));
    }


    // TODO despues de que el sistema crea el usuario, debe enviar un correo eléctronico con un token de verificacion donde el nuevo usuario podrá asignar su nueva contraseña y activar su cuenta
    @Override
    public UsuarioAutenticado crearUsuarioAutenticado(NuevoUsuarioAutenticadoDto nuevoUsuarioDto) {
        validarCamposDto(nuevoUsuarioDto);
        UsuarioAutenticado usuarioAGuardar = mapeadorUsuario.aNuevoUsuarioAutenticado(nuevoUsuarioDto);
        usuarioAGuardar.cambiarContrasena("Contra1234*"); // TODO cambiar a generador de contrasenas
        if (usuarioAGuardar.validarCrearUsuarioAutenticado() && usuarioAGuardar.getUsuario().validarUsuario()) {
            usuarioAGuardar.guardarClaveEncriptada(passwordEncoder.encode(usuarioAGuardar.getContrasena()));

            String mensaje = Mensajes.getString("usuarios.info.crear.usuario");
            logger.info(mensaje, usuarioAGuardar);

            return devolverUsuarioSinClave(repositorioUsuario.crearUsuarioAutenticado(usuarioAGuardar));
        } else
            throw new IllegalArgumentException(VALIDACION_DATOS_OBLIGATORIOS);
    }

    @Override
    public UsuarioAutenticado editarInformacionBasica(Long idUsuario, NuevoUsuarioAutenticadoDto editarUsuario) {
        validarCamposDto(editarUsuario);
        UsuarioAutenticado existente = repositorioUsuario.buscarUsuarioPorIdUsuario(idUsuario);
        UsuarioAutenticado aGuardar = mapeadorUsuario.aEditarUsuarioAutenticado(existente, editarUsuario);
        if (!aGuardar.validarEditarUsuarioAutenticado() && !aGuardar.getUsuario().validarUsuario())
            throw new IllegalArgumentException(VALIDACION_DATOS_OBLIGATORIOS);

        String mensaje = Mensajes.getString("usuarios.info.editar.usuario", aGuardar.getNombreUsuario());
        logger.info(mensaje, aGuardar);
        return devolverUsuarioSinClave(repositorioUsuario.editarInformacionUsuario(aGuardar));
    }

    @Secured("ROLE_ADMIN")
    @Override
    public Boolean adminDesactivaUsuario(Long idUsuario) {
        UsuarioAutenticado cuenta = this.repositorioUsuario.buscarUsuarioPorIdUsuario(idUsuario);
        cuenta = this.repositorioUsuario.cambiarEstado(cuenta, Boolean.FALSE);

        String mensaje = Mensajes.getString("usuarios.info.cambio.estado.exito", idUsuario, "false");
        logger.info(mensaje);

        return cuenta.getActivo();
    }

    @Secured("ROLE_ADMIN")
    @Override
    public boolean cambiarEstado(Long idUsuario, boolean activar) {
        UsuarioAutenticado usuario = repositorioUsuario.buscarUsuarioPorIdUsuario(idUsuario);
        Boolean nuevoEstado = activar;
        if (!usuario.getActivo().equals(activar)) {
            if (activar) {
                String nuevaContrasena = generarContrasenaSegura();
                usuario.cambiarContrasena(nuevaContrasena);
                usuario.guardarClaveEncriptada(passwordEncoder.encode(nuevaContrasena));
                // TODO si el cambio es para activar el usuario nuevamente, se debe enviar correo de verificacion y generar nueva clave
            }
            UsuarioAutenticado nuevoUsuario = repositorioUsuario.cambiarEstado(usuario, activar);
            nuevoEstado = nuevoUsuario.getActivo();
        }

        String mensaje = Mensajes.getString("usuarios.info.cambio.estado.exito", idUsuario, activar);
        logger.info(mensaje);
        return nuevoEstado;
    }

    private void validarCamposDto(Dto dto) {
        if (!dto.validarDto())
            throw new IllegalArgumentException(Mensajes.getString("utilidad.error.dto.faltan.campos.obligatorios", "usuario"));
    }

    private UsuarioAutenticado devolverUsuarioSinClave(UsuarioAutenticado usuario) {
        usuario.limpiarContrasena();
        return usuario;
    }

    private String generarContrasenaSegura() {
        return GeneradorContrasena.generarContrasenaSegura();
    }
}
