package com.asisge.consultifybackend.notificaciones.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.notificaciones.dominio.modelo.Notificacion;
import com.asisge.consultifybackend.notificaciones.dominio.puerto.RepositorioNotificacion;
import com.asisge.consultifybackend.notificaciones.infraestructura.adaptador.convertidor.ConvertidorNotificacion;
import com.asisge.consultifybackend.notificaciones.infraestructura.adaptador.entidad.EntidadNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioNotificacionJPA extends JpaRepository<EntidadNotificacion, Long>, RepositorioNotificacion {

    // metodos JPQL
    List<EntidadNotificacion> findByIdUsuario(Long idUsuario);

    List<EntidadNotificacion> findByIdProyecto(Long idProyecto);


    // metodos propios
    @Override
    default List<Notificacion> obtenerNotificacionesUsuario(Long idUsuario) {
        List<EntidadNotificacion> notificaciones = this.findByIdUsuario(idUsuario);
        return notificaciones.stream().map(ConvertidorNotificacion::aDominio).toList();
    }


    @Override
    default List<Notificacion> obtenerNotificacionesProyecto(Long idProyecto) {
        List<EntidadNotificacion> notificaciones = this.findByIdProyecto(idProyecto);
        return notificaciones.stream().map(ConvertidorNotificacion::aDominio).toList();
    }


    @Override
    default void eliminarNotificacion(Long idNotificacion) {
        this.deleteById(idNotificacion);
    }

    @Override
    default void crearNotificacion(Notificacion notificacion) {
        this.save(ConvertidorNotificacion.aEntidad(notificacion));
    }
}
