package com.asisge.consultifybackend.autenticacion.aplicacion.manejador;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.AuthenticationRequest;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.AuthenticationResponse;
import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioToken;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.TokenVerificacion;
import com.asisge.consultifybackend.autenticacion.dominio.puerto.RepositorioAutorizacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.ServicioCorreo;
import com.asisge.consultifybackend.utilidad.dominio.excepcion.AccionNoPermitidaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Service
public class ManejadorServicioAutenticacion implements ServicioAutenticacion {

    private final Logger logger = LoggerFactory.getLogger(ManejadorServicioAutenticacion.class.getName());

    private final RepositorioAutorizacion repositorioAutorizacion;
    private final AuthenticationManager authenticationManager;
    private final ServicioJWT servicioJWT;
    private final ServicioToken servicioToken;
    private final ServicioCorreo servicioCorreo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ManejadorServicioAutenticacion(RepositorioAutorizacion repositorioAutorizacion, AuthenticationManager authenticationManager,
                                          ServicioJWT servicioJWT, ServicioToken servicioToken,
                                          ServicioCorreo servicioCorreo, PasswordEncoder passwordEncoder) {
        this.repositorioAutorizacion = repositorioAutorizacion;
        this.authenticationManager = authenticationManager;
        this.servicioJWT = servicioJWT;
        this.servicioToken = servicioToken;
        this.servicioCorreo = servicioCorreo;
        this.passwordEncoder = passwordEncoder;
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String obtenerNombreUsuarioEnSesion() {
        return estaAutenticado()
                ? getAuthentication().getName()
                : null;
    }

    @Override
    public boolean estaAutenticado() {
        return getAuthentication().isAuthenticated();
    }


    @Override
    public void recuperarContrasena(String correo) {
        UsuarioAutenticado usuario = repositorioAutorizacion.buscarPorCorreo(correo);
        TokenVerificacion token = servicioToken.crearTokenVerificacion(usuario);

        // preparar correo
        String to = usuario.getUsuario().getCorreo();
        String subject = Mensajes.getString("autenticacion.subject.recuperar.contrasena", to);
        servicioCorreo.enviarCorreoRecuperacion(to, subject, token);

    }


    @Override
    public void reiniciarClave(String token, String contrasena) {
        TokenVerificacion tokenVerificacion = servicioToken.obtenerToken(token);
        servicioToken.validarToken(tokenVerificacion);

        Long idUsuario = tokenVerificacion.getUsuario().getIdUsuario();
        UsuarioAutenticado existente = repositorioAutorizacion.buscarPorIdUsuario(idUsuario);

        if (passwordEncoder.matches(contrasena, existente.getContrasena()))
            throw new AccionNoPermitidaException(Mensajes.getString("cuenta.error.contrasena.igual.anterior"));

        existente.cambiarContrasena(contrasena);
        existente.guardarClaveEncriptada(passwordEncoder.encode(existente.getContrasena()));
        existente.setVerificado(Boolean.TRUE);

        repositorioAutorizacion.guardarDatosUsuario(existente);

        String mensaje = Mensajes.getString("cuenta.info.cambiar.contrasena.exitoso", existente.getNombreUsuario());
        logger.info(mensaje);

        servicioToken.eliminarToken(tokenVerificacion);
    }

    @Override
    public AuthenticationResponse iniciarSesion(AuthenticationRequest authRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getNombreUsuario(), authRequest.getContrasena()
        );
        authenticationManager.authenticate(authToken);
        UsuarioAutenticado usuario = repositorioAutorizacion.buscarPorNombreUsuarioOCorreo(authRequest.getNombreUsuario());
        String jwt = servicioJWT.generarToken(usuario);
        repositorioAutorizacion.actualizarUltimoInicioSesion(usuario);

        String mensaje = Mensajes.getString("autenticacion.info.nuevo.inicio.sesion",
                usuario.getNombreUsuario(),
                LocalDateTime.now(ZoneId.systemDefault()).toString());
        logger.info(mensaje);
        return new AuthenticationResponse(jwt);
    }


    @Override
    public void verificarNuevaCuenta(Long idUsuario, String contrasena, String token) {
        TokenVerificacion tokenVerificacion = servicioToken.obtenerToken(token);
        servicioToken.validarToken(tokenVerificacion);

        UsuarioAutenticado existente = repositorioAutorizacion.buscarPorIdUsuario(idUsuario);
        existente.setVerificado(Boolean.TRUE);
        existente.cambiarContrasena(contrasena);
        existente.guardarClaveEncriptada(passwordEncoder.encode(contrasena));

        repositorioAutorizacion.guardarDatosUsuario(existente);

        String mensaje = Mensajes.getString("cuenta.info.verificar.cuenta.exitoso", existente.getNombreUsuario());
        logger.info(mensaje);

        servicioToken.eliminarToken(tokenVerificacion);
    }

    @Override
    public void verificarCorreoCuenta(String token) {
        TokenVerificacion tokenVerificacion = servicioToken.obtenerToken(token);
        servicioToken.validarToken(tokenVerificacion);

        Long idUsuario = tokenVerificacion.getUsuario().getIdUsuario();
        UsuarioAutenticado existente = repositorioAutorizacion.buscarPorIdUsuario(idUsuario);
        existente.setVerificado(Boolean.TRUE);

        repositorioAutorizacion.guardarDatosUsuario(existente);

        String mensaje = Mensajes.getString("cuenta.info.verificar.cuenta.exitoso", existente.getNombreUsuario());
        logger.info(mensaje);

        servicioToken.eliminarToken(tokenVerificacion);
    }
}
