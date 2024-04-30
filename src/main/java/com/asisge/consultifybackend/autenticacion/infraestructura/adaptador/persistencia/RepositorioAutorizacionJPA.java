package com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.autenticacion.dominio.puerto.RepositorioAutorizacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.convertidor.ConvertidorUsuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioAutorizacionJPA extends JpaRepository<EntidadUsuario, Long>, RepositorioAutorizacion {

    // metodos de JPA
    Optional<EntidadUsuario> findByCorreo(String correo);

    Optional<EntidadUsuario> findByCorreoOrNombreUsuario(String correo, String nombreUsuario);

    Optional<EntidadUsuario> findByIdUsuarioAndCorreo(Long idUsuario, String correo);


    // metodos propios
    @Override
    default UsuarioAutenticado buscarPorCorreo(String correo) {
        EntidadUsuario entidad = findByCorreo(correo).orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con correo " + correo));
        return ConvertidorUsuario.aDominio(entidad, entidad.getCreadoPor().toString());
    }

    @Override
    default UsuarioAutenticado buscarPorNombreUsuarioOCorreo(String correoOUsername) {
        EntidadUsuario entidad = findByCorreoOrNombreUsuario(correoOUsername, correoOUsername).orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario"));
        return ConvertidorUsuario.aDominio(entidad, entidad.getCreadoPor().toString());
    }

    @Override
    default UsuarioAutenticado buscarPorIdUsuarioAndCorreo(Long idUsuario, String correo) {
        EntidadUsuario entidad = findByIdUsuarioAndCorreo(idUsuario, correo)
                .orElseThrow(() -> new EntityNotFoundException("La combinación de datos id/correo no coincide con ninguna en la base de datos"));
        return ConvertidorUsuario.aDominio(entidad, entidad.getCreadoPor().toString());
    }

    @Override
    default UsuarioAutenticado buscarPorIdUsuario(Long idUsuario) {
        EntidadUsuario entidad = findById(idUsuario).orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario"));
        return ConvertidorUsuario.aDominio(entidad, entidad.getCreadoPor().toString());
    }

    @Override
    default UsuarioAutenticado editarCorreo(Usuario usuario) {
        EntidadUsuario actual = findById(usuario.getIdUsuario()).orElseThrow(EntityNotFoundException::new);
        actual.setCorreo(usuario.getCorreo());
        actual.setVerificado(Boolean.FALSE);
        return ConvertidorUsuario.aDominio(this.save(actual), actual.getCreadoPor().toString());
    }

    @Override
    default void cambiarContrasena(UsuarioAutenticado usuarioAutenticado) {
        this.save(ConvertidorUsuario.aEntidad(usuarioAutenticado.getUsuario(), usuarioAutenticado));
    }
}
