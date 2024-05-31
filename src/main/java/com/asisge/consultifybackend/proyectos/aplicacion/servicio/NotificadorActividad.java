package com.asisge.consultifybackend.proyectos.aplicacion.servicio;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import org.springframework.scheduling.annotation.Async;

public interface NotificadorActividad {

    @Async
    void notificarResponsableActividad(Actividad actividad);
}
