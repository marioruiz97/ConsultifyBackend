package com.asisge.consultifybackend.auth.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.dominio.modelo.Usuario;
import com.asisge.consultifybackend.auth.dominio.puerto.RepositorioAutorizacion;
import com.asisge.consultifybackend.infraestructura.adaptador.convertidor.ConvertidorUsuario;
import com.asisge.consultifybackend.infraestructura.adaptador.entidad.EntidadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioAutorizacionJPA extends JpaRepository<EntidadUsuario, Long>, RepositorioAutorizacion {

    // metodos de JPA
    Optional<EntidadUsuario> findByCorreo(String correo);


    // metodos propios
    @Override
    default Usuario buscarPorCorreo(String correo) {
        EntidadUsuario entidad = findByCorreo(correo).orElse(null);
        return ConvertidorUsuario.aDominio(entidad);
    }
}
