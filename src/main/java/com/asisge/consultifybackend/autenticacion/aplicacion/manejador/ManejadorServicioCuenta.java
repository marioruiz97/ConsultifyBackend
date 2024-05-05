package com.asisge.consultifybackend.autenticacion.aplicacion.manejador;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.ActualizarMisDatosDto;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.CambioContrasenaDto;
import com.asisge.consultifybackend.autenticacion.aplicacion.dto.CambioCorreoDto;
import com.asisge.consultifybackend.autenticacion.aplicacion.mapeador.MapeadorCuenta;
import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioCuenta;
import com.asisge.consultifybackend.autenticacion.dominio.modelo.MisDatos;
import com.asisge.consultifybackend.autenticacion.dominio.puerto.RepositorioAutorizacion;
import com.asisge.consultifybackend.utilidad.dominio.modelo.Dto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.dominio.puerto.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManejadorServicioCuenta implements ServicioCuenta {

    public static final String VALIDACION_DATOS_OBLIGATORIOS = "El objeto no pasó la validación de usuario. Verifica los datos obligatorios y el formato de teléfono/correo";

    private final RepositorioAutorizacion repositorioAutorizacion;
    private final PasswordEncoder passwordEncoder;
    private final RepositorioUsuario repositorioUsuario;
    private final MapeadorCuenta mapeadorCuenta;

    @Autowired
    public ManejadorServicioCuenta(RepositorioAutorizacion repositorioAutorizacion,
                                   RepositorioUsuario repositorioUsuario,
                                   PasswordEncoder passwordEncoder,
                                   MapeadorCuenta mapeadorCuenta) {
        this.repositorioAutorizacion = repositorioAutorizacion;
        this.passwordEncoder = passwordEncoder;
        this.repositorioUsuario = repositorioUsuario;
        this.mapeadorCuenta = mapeadorCuenta;
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
        return mapeadorCuenta.aMisDatos(repositorioUsuario.editarInformacionUsuario(aGuardar));
    }

    @Override
    public Boolean desactivarMiUsuario(Long idUsuario) {
        UsuarioAutenticado cuenta = repositorioAutorizacion.buscarPorIdUsuario(idUsuario);
        cuenta = repositorioUsuario.cambiarEstado(cuenta, Boolean.FALSE);
        return cuenta.getActivo();
    }

    @Override
    public void cambiarContrasena(Long idUsuario, CambioContrasenaDto usuarioDto) {
        validarCamposDto(usuarioDto);
        UsuarioAutenticado existente = repositorioAutorizacion.buscarPorIdUsuarioAndCorreo(idUsuario, usuarioDto.getCorreo());

        if (!passwordEncoder.matches(usuarioDto.getContrasenaActual(), existente.getContrasena()))
            throw new IllegalArgumentException("La contraseña actual proporcionada no coincide con la almacenada en la base de datos.");
        if (existente.getContrasena().equals(usuarioDto.getContrasena()))
            throw new IllegalArgumentException("La nueva contraseña no puede ser igual a la anterior, por favor verifique los datos");

        existente.cambiarContrasena(usuarioDto.getContrasena());
        existente.guardarClaveEncriptada(passwordEncoder.encode(existente.getContrasena()));
        repositorioAutorizacion.cambiarContrasena(existente);
    }

    @Override
    public MisDatos cambiarCorreoElectronico(Long idUsuario, CambioCorreoDto usuarioDto) {
        validarCamposDto(usuarioDto);
        UsuarioAutenticado cuenta = repositorioAutorizacion.buscarPorIdUsuarioAndCorreo(idUsuario, usuarioDto.getCorreoActual());
        Usuario usuario = cuenta.getUsuario();
        usuario.cambiarCorreo(usuarioDto.getCorreoNuevo());
        validarUsuario(usuario);
        return mapeadorCuenta.aMisDatos(repositorioAutorizacion.editarCorreo(usuario));
        // TODO enviar correo pidiendo verificacion del nuevo correo y finalizar sesion (hacer el token actual invalido)
    }

    private void validarCamposDto(Dto dto) {
        if (!dto.validarDto())
            throw new IllegalArgumentException("Por favor valide los datos obligatorios del usuario DTO");
    }

    private void validarUsuario(Usuario existente) {
        if (!existente.validarUsuario())
            throw new IllegalArgumentException(VALIDACION_DATOS_OBLIGATORIOS);
    }
}
