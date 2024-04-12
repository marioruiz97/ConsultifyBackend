package com.asisge.consultifybackend.autenticacion.aplicacion.manejador;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.AuthenticationRequest;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.AuthenticationResponse;
import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import com.asisge.consultifybackend.autenticacion.dominio.puerto.RepositorioAutorizacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ManejadorServicioAutenticacion implements ServicioAutenticacion {

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
        Usuario usuario = buscarUsuarioPorCorreo(correo);
        /*
        VerificationToken token = service.validVerificationToken(usuario);
        emailService.sendRecoveryPassword(token);
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
        return new AuthenticationResponse(jwt);
    }

    private Map<String, Object> agregarExtraClaims(UsuarioAutenticado usuarioAutenticado) {
        Map<String, Object> extraClaims = new HashMap<>();
        Usuario usuario = usuarioAutenticado.getUsuario();

        extraClaims.put("id_usuario", usuario.getIdUsuario());
        extraClaims.put("nombre", usuario.getNombres() + " " + usuario.getApellidos());
        extraClaims.put("rol", usuarioAutenticado.getRol().name());
        extraClaims.put("correo", usuario.getCorreo());
        extraClaims.put("activo", usuarioAutenticado.getActivo());

        return extraClaims;
    }

    private Usuario buscarUsuarioPorCorreo(String correo) {
        UsuarioAutenticado usuario = repositorioAutorizacion.buscarPorCorreo(correo);
        if (usuario == null)
            throw new EntityNotFoundException(String.format("No se encontro el correo electronico %s en la base de datos", correo));
        return usuario.getUsuario();
    }
}
