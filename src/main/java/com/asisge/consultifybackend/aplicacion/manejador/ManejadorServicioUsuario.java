package com.asisge.consultifybackend.aplicacion.manejador;

import com.asisge.consultifybackend.aplicacion.dto.CambioContrasenaDto;
import com.asisge.consultifybackend.aplicacion.dto.NuevoUsuarioAutenticadoDto;
import com.asisge.consultifybackend.aplicacion.dto.UsuarioBasicoDto;
import com.asisge.consultifybackend.aplicacion.mapeador.MapeadorUsuario;
import com.asisge.consultifybackend.aplicacion.servicio.ServicioUsuario;
import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.dominio.puerto.RepositorioUsuario;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManejadorServicioUsuario implements ServicioUsuario {

    private final RepositorioUsuario repositorioUsuario;
    private final MapeadorUsuario mapeadorUsuario;

    @Autowired
    public ManejadorServicioUsuario(RepositorioUsuario repositorioUsuario, MapeadorUsuario mapeadorUsuario) {
        this.repositorioUsuario = repositorioUsuario;
        this.mapeadorUsuario = mapeadorUsuario;
    }


    @Override
    public UsuarioAutenticado crearUsuarioAutenticado(NuevoUsuarioAutenticadoDto nuevoUsuarioDto) {
        if (!nuevoUsuarioDto.validarDto())
            throw new IllegalArgumentException("Por favor valide los datos obligatorios del usuario DTO");
        UsuarioAutenticado usuarioAGuardar = mapeadorUsuario.aUsuarioAutenticado(nuevoUsuarioDto);
        if (usuarioAGuardar.validarUsuarioAutenticado() && usuarioAGuardar.getUsuario().validarUsuario()) {
            UsuarioAutenticado guardado = repositorioUsuario.crearUsuarioAutenticado(usuarioAGuardar);
            guardado.limpiarContrasena();
            return guardado;
        } else
            throw new IllegalArgumentException("El objeto no pasó la validación de usuario. Verifica los datos obligatorios y el formato de teléfono/correo");
    }

    @Override
    public UsuarioAutenticado editarInformacionBasica(Long idUsuario, UsuarioBasicoDto editarUsuario) {
        if (!editarUsuario.validarDto())
            throw new IllegalArgumentException("Por favor valide los datos obligatorios del usuario DTO");
        Usuario existente = repositorioUsuario.buscarUsuarioPorId(idUsuario);
        if (!existente.getIdentificacion().equals(editarUsuario.getIdentificacion()))
            throw new EntityNotFoundException("La identificación del usuario ingresado no corresponde a los datos en base de datos. Por favor verifíquelos");
        Usuario aGuardar = new Usuario(idUsuario, existente.getIdentificacion(), editarUsuario.getNombres(), editarUsuario.getApellidos(), editarUsuario.getTelefono(),
                editarUsuario.getCorreo());
        if (!aGuardar.validarUsuario())
            throw new IllegalArgumentException("El objeto no pasó la validación de usuario. Verifica los datos obligatorios y el formato de teléfono/correo");
        return repositorioUsuario.editarInformacionBasica(aGuardar);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return repositorioUsuario.buscarTodos();
    }

    @Override
    public List<UsuarioAutenticado> buscarTodosAutenticados() {
        return repositorioUsuario.buscarTodosUsuariosAutenticados().stream().map(mapeadorUsuario::aUsuarioAutenticadoDto).toList();
    }

    @Override
    public UsuarioAutenticado buscarUsuarioPorIdentificacion(String identificacion) {
        return mapeadorUsuario.aUsuarioAutenticadoDto(repositorioUsuario.buscarUsuarioPorIdentificacion(identificacion));
    }

    @Override
    public UsuarioAutenticado buscarUsuarioPorCorreo(String correo) {
        return repositorioUsuario.buscarUsuarioPorCorreo(correo);
    }

    @Override
    public void eliminarUsuario(String identificacion) {
        repositorioUsuario.eliminarUsuario(identificacion);
    }

    @Override
    public void cambiarContrasena(CambioContrasenaDto usuarioAutenticado) {
        repositorioUsuario.cambiarContrasena(mapeadorUsuario.cambioContrasena(usuarioAutenticado));
    }

}
