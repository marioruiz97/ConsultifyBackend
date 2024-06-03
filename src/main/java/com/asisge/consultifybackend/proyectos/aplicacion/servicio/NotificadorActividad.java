package com.asisge.consultifybackend.proyectos.aplicacion.servicio;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.ActividadDto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import org.springframework.scheduling.annotation.Async;

public interface NotificadorActividad {

    @Async
    void notificarResponsableActividad(Actividad actividad, ActividadDto dto, String usuarioEnSesion);
}
