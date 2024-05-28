package com.asisge.consultifybackend.autenticacion.aplicacion.manejador;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.ActualizarMisDatosDto;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.CambioContrasenaDto;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.CambioCorreoDto;
import com.asisge.consultifybackend.autenticacion.aplicacion.mapeador.MapeadorCuenta;
import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioCuenta;
import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioToken;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.MisDatos;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.TokenVerificacion;
import com.asisge.consultifybackend.autenticacion.dominio.puerto.RepositorioAutorizacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.dominio.puerto.RepositorioUsuario;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.ServicioCorreo;
import com.asisge.consultifybackend.utilidad.dominio.excepcion.AccionNoPermitidaException;
import com.asisge.consultifybackend.utilidad.dominio.modelo.Dto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class ManejadorServicioCuenta implements ServicioCuenta {

    public static final String VALIDACION_DATOS_OBLIGATORIOS = "El objeto no pasó la validación de usuario. Verifica los datos obligatorios y el formato de teléfono/correo";
    private final Logger logger = LoggerFactory.getLogger(ManejadorServicioCuenta.class);

    private final RepositorioAutorizacion repositorioAutorizacion;
    private final PasswordEncoder passwordEncoder;
    private final RepositorioUsuario repositorioUsuario;
    private final MapeadorCuenta mapeadorCuenta;
    private final ServicioToken servicioToken;
    private final ServicioCorreo servicioCorreo;

    @Autowired
    public ManejadorServicioCuenta(RepositorioAutorizacion repositorioAutorizacion,
                                   RepositorioUsuario repositorioUsuario,
                                   PasswordEncoder passwordEncoder,
                                   MapeadorCuenta mapeadorCuenta,
                                   ServicioToken servicioToken,
                                   ServicioCorreo servicioCorreo) {
        this.repositorioAutorizacion = repositorioAutorizacion;
        this.passwordEncoder = passwordEncoder;
        this.repositorioUsuario = repositorioUsuario;
        this.mapeadorCuenta = mapeadorCuenta;
        this.servicioToken = servicioToken;
        this.servicioCorreo = servicioCorreo;
    }

    @Override
    public MisDatos buscarPorIdUsuario(Long idUsuario) {
        UsuarioAutenticado cuenta = repositorioAutorizacion.buscarPorIdUsuario(idUsuario);
        return mapeadorCuenta.aMisDatos(cuenta);
    }

    @Override
    public MisDatos editarMiInformacionBasica(Long idUsuario, ActualizarMisDatosDto editarUsuario) {
        validarCamposDto(editarUsuario);
        UsuarioAutenticado existente = repositorioUsuario.buscarUsuarioPorIdUsuario(idUsuario);
        UsuarioAutenticado aGuardar = mapeadorCuenta.aUsuarioAutenticado(existente, editarUsuario);
        if (!aGuardar.validarEditarUsuarioAutenticado() && !aGuardar.getUsuario().validarUsuario())
            throw new IllegalArgumentException(VALIDACION_DATOS_OBLIGATORIOS);

        String mensaje = Mensajes.getString("cuenta.info.editar.usuario.exito", aGuardar.getNombreUsuario());
        logger.info(mensaje);
        return mapeadorCuenta.aMisDatos(repositorioUsuario.editarInformacionUsuario(aGuardar));
    }

    @Override
    public Boolean desactivarMiUsuario(Long idUsuario) {
        UsuarioAutenticado cuenta = repositorioAutorizacion.buscarPorIdUsuario(idUsuario);
        cuenta = repositorioUsuario.cambiarEstado(cuenta, Boolean.FALSE);

        String mensaje = Mensajes.getString("cuenta.info.desactivar.usuario.exito", cuenta.getNombreUsuario());
        logger.info(mensaje);
        return cuenta.getActivo();
    }

    @Override
    public void cambiarContrasena(Long idUsuario, CambioContrasenaDto usuarioDto) {
        validarCamposDto(usuarioDto);
        UsuarioAutenticado existente = repositorioAutorizacion.buscarPorIdUsuarioAndCorreo(idUsuario, usuarioDto.getCorreo());

        if (!passwordEncoder.matches(usuarioDto.getContrasenaActual(), existente.getContrasena()))
            throw new AccionNoPermitidaException(Mensajes.getString("cuenta.error.contrasena.actual.nocoincide"));
        if (existente.getContrasena().equals(usuarioDto.getContrasena()))
            throw new AccionNoPermitidaException(Mensajes.getString("cuenta.error.contrasena.igual.anterior"));

        existente.cambiarContrasena(usuarioDto.getContrasena());
        existente.guardarClaveEncriptada(passwordEncoder.encode(existente.getContrasena()));
        repositorioAutorizacion.cambiarContrasena(existente);

        String mensaje = Mensajes.getString("cuenta.info.cambiar.contrasena.exitoso", existente.getNombreUsuario());
        logger.info(mensaje);
    }

    @Override
    public MisDatos cambiarCorreoElectronico(Long idUsuario, CambioCorreoDto usuarioDto) {
        validarCamposDto(usuarioDto);

        Usuario usuario = repositorioAutorizacion.buscarPorIdUsuarioAndCorreo(idUsuario, usuarioDto.getCorreoActual()).getUsuario();
        usuario.cambiarCorreo(usuarioDto.getCorreoNuevo());
        validarUsuario(usuario);

        UsuarioAutenticado cuenta = repositorioAutorizacion.editarCorreo(usuario);
        MisDatos misDatos = mapeadorCuenta.aMisDatos(cuenta);
        String mensaje = Mensajes.getString("cuenta.info.cambiar.correo.exitoso", cuenta.getNombreUsuario(), misDatos.getCorreo());
        logger.info(mensaje, misDatos);

        //envio de correo
        TokenVerificacion token = servicioToken.crearTokenVerificacion(cuenta);
        String to = misDatos.getCorreo();
        String subject = Mensajes.getString("cuenta.subject.verificar.cuenta", to);

        servicioCorreo.enviarCorreoVerificacion(to, subject, token);

        return misDatos;
    }

    private void validarCamposDto(Dto dto) {
        if (!dto.validarDto())
            throw new IllegalArgumentException(Mensajes.getString("utilidad.error.dto.faltan.campos.obligatorios", "usuario"));
    }

    private void validarUsuario(Usuario existente) {
        if (!existente.validarUsuario())
            throw new IllegalArgumentException(VALIDACION_DATOS_OBLIGATORIOS);
    }
}
