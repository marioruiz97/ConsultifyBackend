package com.asisge.consultifybackend.aplicacion.manejador;

import com.asisge.consultifybackend.aplicacion.dto.CambioContrasenaDto;
import com.asisge.consultifybackend.aplicacion.dto.NuevoUsuarioAutenticadoDto;
import com.asisge.consultifybackend.aplicacion.mapeador.MapeadorUsuario;
import com.asisge.consultifybackend.aplicacion.servicio.ServicioUsuario;
import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.dominio.puerto.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public UsuarioAutenticado crearUsuarioAutenticado(NuevoUsuarioAutenticadoDto nuevoUsuario) {
        return repositorioUsuario.crearUsuarioAutenticado(mapeadorUsuario.aUsuarioAutenticado(nuevoUsuario));
    }

    @Override
    public List<Usuario> buscarTodos() {
        return repositorioUsuario.buscarTodos();
    }

    @Override
    public List<UsuarioAutenticado> buscarTodosAutenticados() {
        return repositorioUsuario.buscarTodosUsuariosAutenticados().stream().map(mapeadorUsuario::aUsuarioAutenticadoDto).collect(Collectors.toList());
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
