package com.asisge.consultifybackend.proyectos.aplicacion.servicio;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import org.springframework.scheduling.annotation.Async;

public interface NotificadorProyecto {

    @Async
    void notificarNuevoProyecto(Proyecto nuevoProyecto);

    @Async
    void notificarNuevoMiembroProyecto(Long idProyecto, UsuarioAutenticado miembro);

    @Async
    void notificarEliminarMiembroProyecto(Long idProyecto, Long idMiembro);

}
