package com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.autenticacion.dominio.modelo.TokenVerificacion;
import com.asisge.consultifybackend.autenticacion.dominio.puerto.RepositorioToken;
import com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.convertidor.ConvertidorToken;
import com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.entidad.EntidadTokenVerificacion;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.convertidor.ConvertidorUsuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioTokenJPA extends JpaRepository<EntidadTokenVerificacion, Long>, RepositorioToken {

    // JPQL
    Optional<EntidadTokenVerificacion> findByToken(String token);


    // metodos propios
    @Override
    default TokenVerificacion crearTokenVerificacion(UsuarioAutenticado usuario) {
        EntidadUsuario usuarioToken = ConvertidorUsuario.aEntidad(usuario.getUsuario(), usuario);
        EntidadTokenVerificacion entidad = this.save(new EntidadTokenVerificacion(usuarioToken));
        return ConvertidorToken.aDominio(entidad);
    }

    @Override
    default TokenVerificacion obtenerToken(String token) {
        EntidadTokenVerificacion entidad = this.findByToken(token).orElseThrow(() -> new EntityNotFoundException("No se encontro el token en base de datos"));
        return ConvertidorToken.aDominio(entidad);
    }

    @Override
    default void eliminarToken(TokenVerificacion tokenVerificacion) {
        this.deleteById(tokenVerificacion.getIdToken());
    }
}
