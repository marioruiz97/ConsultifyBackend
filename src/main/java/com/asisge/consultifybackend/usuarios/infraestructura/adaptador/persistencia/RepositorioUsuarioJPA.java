package com.asisge.consultifybackend.usuarios.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.dominio.puerto.RepositorioUsuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.convertidor.ConvertidorUsuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioUsuarioJPA extends JpaRepository<EntidadUsuario, Long>, RepositorioUsuario {

    // Métodos de JPA
    Optional<EntidadUsuario> findByCorreoOrNombreUsuario(String correo, String nombreUsuario);

    // Métodos propios
    @Override
    default UsuarioAutenticado crearUsuarioAutenticado(UsuarioAutenticado usuarioAutenticado) {
        EntidadUsuario nuevoUsuario = this.save(ConvertidorUsuario.aEntidad(usuarioAutenticado.getUsuario(), usuarioAutenticado));
        return ConvertidorUsuario.aDominio(nuevoUsuario, usuarioAutenticado.getCreadoPor());
    }

    @Override
    default UsuarioAutenticado editarInformacionUsuario(UsuarioAutenticado cuenta) {
        Usuario usuario = cuenta.getUsuario();
        EntidadUsuario existente = findById(usuario.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("No se a encontrado el usuario en base de datos"));
        EntidadUsuario aGuardar = ConvertidorUsuario.aEntidad(usuario, cuenta);
        aGuardar.setCreadoPor(existente.getCreadoPor());
        return ConvertidorUsuario.aDominio(this.save(aGuardar), aGuardar.getCreadoPor().toString());
    }

    @Override
    default UsuarioAutenticado cambiarEstado(UsuarioAutenticado usuario, boolean activo) {
        EntidadUsuario aGuardar = ConvertidorUsuario.aEntidad(usuario.getUsuario(), usuario);
        aGuardar.setActivo(activo);
        return ConvertidorUsuario.aDominio(this.save(aGuardar), aGuardar.getCreadoPor().toString());
    }

    @Override
    default List<UsuarioAutenticado> buscarTodosUsuariosAutenticados() {
        return this.findAll().stream().map(entidad -> ConvertidorUsuario.aDominio(entidad, entidad.getCreadoPor().toString())).toList();
    }

    @Override
    default UsuarioAutenticado buscarPorCorreoOUsername(String correoOUsername) {
        EntidadUsuario entidad = findByCorreoOrNombreUsuario(correoOUsername, correoOUsername).orElse(null);
        return ConvertidorUsuario.aDominio(entidad, "1");
    }

    @Override
    default UsuarioAutenticado buscarUsuarioPorIdUsuario(Long idUsuario) {
        EntidadUsuario usuario = this.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario en base de datos"));
        return ConvertidorUsuario.aDominio(usuario, usuario.getCreadoPor().toString());
    }
}
