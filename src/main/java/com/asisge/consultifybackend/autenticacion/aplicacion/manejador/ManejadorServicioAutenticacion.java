package com.asisge.consultifybackend.autenticacion.aplicacion.manejador;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.AuthenticationRequest;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.AuthenticationResponse;
import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import com.asisge.consultifybackend.autenticacion.dominio.puerto.RepositorioAutorizacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class ManejadorServicioAutenticacion implements ServicioAutenticacion {

    private final Logger logger = Logger.getLogger(ManejadorServicioAutenticacion.class.getName());

    private final RepositorioAutorizacion repositorioAutorizacion;
    private final AuthenticationManager authenticationManager;
    private final ServicioJWT servicioJWT;

    @Autowired
    public ManejadorServicioAutenticacion(RepositorioAutorizacion repositorioAutorizacion, AuthenticationManager authenticationManager, ServicioJWT servicioJWT) {
        this.repositorioAutorizacion = repositorioAutorizacion;
        this.authenticationManager = authenticationManager;
        this.servicioJWT = servicioJWT;
    }

    @Override
    public void recuperarContrasena(String correo) {
        /*
        Usuario usuario = buscarUsuarioPorCorreo(correo)
        VerificationToken token = service.validVerificationToken(usuario)
        emailService.sendRecoveryPassword(token)
         */
    }

    @Override
    public AuthenticationResponse iniciarSesion(AuthenticationRequest authRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getNombreUsuario(), authRequest.getContrasena()
        );
        authenticationManager.authenticate(authToken);
        UsuarioAutenticado usuario = repositorioAutorizacion.buscarPorNombreUsuarioOCorreo(authRequest.getNombreUsuario());
        String jwt = servicioJWT.generarToken(usuario, agregarExtraClaims(usuario));
        repositorioAutorizacion.actualizarUltimoInicioSesion(usuario);

        String mensaje = Mensajes.getString("autenticacion.info.nuevo.inicio.sesion",
                usuario.getNombreUsuario(),
                LocalDateTime.now(ZoneId.systemDefault()).toString());
        logger.info(mensaje);
        return new AuthenticationResponse(jwt);
    }

    private Map<String, Object> agregarExtraClaims(UsuarioAutenticado usuarioAutenticado) {
        Map<String, Object> extraClaims = new HashMap<>();
        Usuario usuario = usuarioAutenticado.getUsuario();

        extraClaims.put("idUsuario", usuario.getIdUsuario());
        extraClaims.put("identificacion", usuario.getIdentificacion());
        extraClaims.put("nombreUsuario", usuarioAutenticado.getNombreUsuario());
        extraClaims.put("nombreCompleto", usuario.getNombres() + " " + usuario.getApellidos());
        extraClaims.put("rol", usuarioAutenticado.getRol().name());
        extraClaims.put("correo", usuario.getCorreo());
        extraClaims.put("activo", usuarioAutenticado.getActivo());

        return extraClaims;
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        UsuarioAutenticado usuario = repositorioAutorizacion.buscarPorCorreo(correo);
        if (usuario == null)
            throw new EntityNotFoundException(Mensajes.getString("autenticacion.error.correo.no.encontrado", correo));
        return usuario.getUsuario();
    }
}
