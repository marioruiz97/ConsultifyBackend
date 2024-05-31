package com.asisge.consultifybackend.notificaciones.aplicacion.manejador;

import com.asisge.consultifybackend.notificaciones.aplicacion.servicio.ServicioNotificacion;
import com.asisge.consultifybackend.notificaciones.dominio.modelo.Notificacion;
import com.asisge.consultifybackend.notificaciones.dominio.puerto.RepositorioNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManejadorServicioNotificacion implements ServicioNotificacion {


    private final RepositorioNotificacion repositorioNotificacion;

    @Autowired
    public ManejadorServicioNotificacion(RepositorioNotificacion repositorioNotificacion) {
        this.repositorioNotificacion = repositorioNotificacion;
    }

    @Override
    public List<Notificacion> obtenerNotificacionesUsuario(Long idUsuario) {
        return repositorioNotificacion.obtenerNotificacionesUsuario(idUsuario);
    }

    @Override
    public List<Notificacion> obtenerNotificacionesProyecto(Long idProyecto) {
        return repositorioNotificacion.obtenerNotificacionesProyecto(idProyecto);
    }

    @Override
    public void eliminarNotificacion(Long idNotificacion) {
        repositorioNotificacion.eliminarNotificacion(idNotificacion);
    }

    @Async
    @Override
    public void crearNotificacion(Notificacion notificacion) {
        repositorioNotificacion.crearNotificacion(notificacion);
    }

}
