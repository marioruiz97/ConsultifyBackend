package com.asisge.consultifybackend.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.dominio.puerto.RepositorioUsuario;
import com.asisge.consultifybackend.infraestructura.adaptador.convertidor.ConvertidorUsuario;
import com.asisge.consultifybackend.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioUsuarioJPA extends JpaRepository<EntidadUsuario, Long>, RepositorioUsuario {

    // Métodos de JPA
    Optional<EntidadUsuario> findByIdentificacion(String identificacion);

    Optional<EntidadUsuario> findByCorreo(String correo);

    Optional<EntidadUsuario> findByCorreoOrNombreUsuario(String correo, String nombreUsuario);

    // Métodos propios
    @Override
    default UsuarioAutenticado crearUsuarioAutenticado(UsuarioAutenticado usuarioAutenticado) {
        EntidadUsuario nuevoUsuario = this.save(ConvertidorUsuario.aEntidad(usuarioAutenticado.getUsuario(), usuarioAutenticado));
        return ConvertidorUsuario.aDominio(nuevoUsuario, usuarioAutenticado.getCreadoPor());
    }

    @Override
    default UsuarioAutenticado editarInformacionBasica(Usuario aGuardar) {
        EntidadUsuario existente = findById(aGuardar.getIdUsuario()).orElseThrow(()-> new EntityNotFoundException("No se a encontrado el usuario en base de datos"));
        existente.setNombres(aGuardar.getNombres());
        existente.setApellidos(aGuardar.getApellidos());
        existente.setTelefono(aGuardar.getTelefono());
        return ConvertidorUsuario.aDominio(this.save(existente), existente.getCreadoPor().toString());
    }

    @Override
    default UsuarioAutenticado editarCorreo(Usuario existente) {
        EntidadUsuario actual = findById(existente.getIdUsuario()).orElseThrow(EntityNotFoundException::new);
        actual.setCorreo(existente.getCorreo());
        actual.setVerificado(Boolean.FALSE);
        return ConvertidorUsuario.aDominio(this.save(actual), actual.getCreadoPor().toString());
    }

    @Override
    default UsuarioAutenticado cambiarEstado(UsuarioAutenticado usuario, boolean activo) {
        EntidadUsuario aGuardar = ConvertidorUsuario.aEntidad(usuario.getUsuario(), usuario);
        aGuardar.setActivo(activo);
        return ConvertidorUsuario.aDominio(this.save(aGuardar), aGuardar.getCreadoPor().toString());
    }

    @Override
    default void eliminarUsuario(String identificacion) {
        // TODO: construir
    }

    @Override
    default void cambiarContrasena(UsuarioAutenticado usuarioAutenticado) {
        this.save(ConvertidorUsuario.aEntidad(usuarioAutenticado.getUsuario(), usuarioAutenticado));
    }

    @Override
    default List<Usuario> buscarTodos() {
        return this.findAll().stream().map(ConvertidorUsuario::aDominio).toList();
    }

    @Override
    default List<UsuarioAutenticado> buscarTodosUsuariosAutenticados() {
        return this.findAll().stream().map(entidad -> ConvertidorUsuario.aDominio(entidad, "creadoPor")).toList();
    }

    @Override
    default UsuarioAutenticado buscarUsuarioPorIdentificacion(String identificacion) {
        EntidadUsuario entidad = this.findByIdentificacion(identificacion).orElse(null);
        if (entidad == null)
            throw new EntityNotFoundException("No se encontró el usuario con identificacion " + identificacion);
        return ConvertidorUsuario.aDominio(entidad, entidad.getCreadoPor().toString());
    }

    @Override
    default UsuarioAutenticado buscarUsuarioPorCorreo(String correo) {
        EntidadUsuario entidad = this.findByCorreo(correo).orElse(null);
        if (entidad == null)
            throw new EntityNotFoundException("No se encontró el usuario con correo " + correo);
        return ConvertidorUsuario.aDominio(entidad, entidad.getCreadoPor().toString());
    }

    @Override
    default UsuarioAutenticado buscarPorCorreoOUsername(String correoOUsername) {
        EntidadUsuario entidad = findByCorreoOrNombreUsuario(correoOUsername, correoOUsername).orElse(null);
        return ConvertidorUsuario.aDominio(entidad, "1");
    }

    @Override
    default Usuario buscarUsuarioPorId(Long idUsuario) {
        return ConvertidorUsuario.aDominio(this.findById(idUsuario).orElseThrow(EntityNotFoundException::new));
    }

}
