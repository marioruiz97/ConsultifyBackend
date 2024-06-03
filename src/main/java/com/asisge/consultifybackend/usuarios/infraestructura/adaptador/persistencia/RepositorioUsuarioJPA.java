package com.asisge.consultifybackend.usuarios.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Rol;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.dominio.puerto.RepositorioUsuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.convertidor.ConvertidorUsuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RepositorioUsuarioJPA extends JpaRepository<EntidadUsuario, Long>, RepositorioUsuario {

    // Métodos de JPA
    Optional<EntidadUsuario> findByCorreoOrNombreUsuario(String correo, String nombreUsuario);

    @Query("SELECT u FROM EntidadUsuario u WHERE u.rol IN (:roles)")
    List<EntidadUsuario> findByRoles(@Param("roles") Set<Rol> roles);

    // Métodos propios
    @Override
    default UsuarioAutenticado crearUsuarioAutenticado(UsuarioAutenticado usuarioAutenticado) {
        EntidadUsuario nuevoUsuario = this.save(ConvertidorUsuario.aEntidad(usuarioAutenticado.getUsuario(), usuarioAutenticado));
        return ConvertidorUsuario.aDominio(nuevoUsuario);
    }

    @Override
    default UsuarioAutenticado editarInformacionUsuario(UsuarioAutenticado cuenta) {
        Usuario usuario = cuenta.getUsuario();
        if (!this.existsById(usuario.getIdUsuario()))
            throw new EntityNotFoundException("No se a encontrado el usuario en base de datos");

        EntidadUsuario aGuardar = ConvertidorUsuario.aEntidad(usuario, cuenta);
        return ConvertidorUsuario.aDominio(this.save(aGuardar));
    }

    @Override
    default UsuarioAutenticado cambiarEstado(UsuarioAutenticado usuario, boolean activo) {
        EntidadUsuario aGuardar = ConvertidorUsuario.aEntidad(usuario.getUsuario(), usuario);
        aGuardar.setActivo(activo);
        return ConvertidorUsuario.aDominio(this.save(aGuardar));
    }

    @Override
    default List<UsuarioAutenticado> buscarTodosUsuariosAutenticados() {
        return this.findAll().stream().map(ConvertidorUsuario::aDominio).toList();
    }

    @Override
    default List<UsuarioAutenticado> asesorBuscarTodosUsuariosAutenticados() {
        List<EntidadUsuario> usuarios = this.findByRoles(Set.of(Rol.ROLE_CLIENTE));
        return usuarios.stream().map(ConvertidorUsuario::aDominio).toList();
    }

    @Override
    default UsuarioAutenticado buscarPorCorreoOUsername(String correoOUsername) {
        EntidadUsuario entidad = findByCorreoOrNombreUsuario(correoOUsername, correoOUsername).orElse(null);
        return ConvertidorUsuario.aDominio(entidad);
    }

    @Override
    default UsuarioAutenticado buscarUsuarioPorIdUsuario(Long idUsuario) {
        EntidadUsuario usuario = this.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario en base de datos"));
        return ConvertidorUsuario.aDominio(usuario);
    }
}
