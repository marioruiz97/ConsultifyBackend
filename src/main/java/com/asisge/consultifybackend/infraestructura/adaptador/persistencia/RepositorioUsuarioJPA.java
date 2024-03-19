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
import java.util.stream.Collectors;

@Repository
public interface RepositorioUsuarioJPA extends JpaRepository<EntidadUsuario, Long>, RepositorioUsuario {

    // Métodos de JPA
    Optional<EntidadUsuario> findByIdentificacion(String identificacion);


    // Métodos propios
    @Override
    default UsuarioAutenticado crearUsuarioAutenticado(UsuarioAutenticado usuarioAutenticado) {
        // TODO: construir
        throw new UnsupportedOperationException("Método no implementado");
    }

    @Override
    default List<Usuario> buscarTodos() {
        return this.findAll().stream().map(ConvertidorUsuario::aDominio).collect(Collectors.toList());
    }

    @Override
    default List<UsuarioAutenticado> buscarTodosUsuariosAutenticados() {
        return this.findAll().stream().map(entidad -> ConvertidorUsuario.aDominio(entidad, "creadoPor")).collect(Collectors.toList());
    }

    @Override
    default UsuarioAutenticado buscarUsuarioPorIdentificacion(String identificacion) {
        EntidadUsuario entidad = this.findByIdentificacion(identificacion).orElse(null);
        if (entidad == null)
            throw new EntityNotFoundException("No se encontró el usuario con identificacion" + identificacion);
        return ConvertidorUsuario.aDominio(entidad, entidad.getCreadoPor().toString());
    }

    @Override
    default UsuarioAutenticado buscarUsuarioPorCorreo(String correo) {
        // TODO: construir
        throw new UnsupportedOperationException("Método no implementado");
    }

    @Override
    default void eliminarUsuario(String identificacion) {
        // TODO: construir
    }

    @Override
    default void cambiarContrasena(UsuarioAutenticado usuarioAutenticado) {
        // TODO: construir
    }
}
