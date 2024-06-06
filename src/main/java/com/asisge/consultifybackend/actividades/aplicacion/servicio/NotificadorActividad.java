package com.asisge.consultifybackend.actividades.aplicacion.servicio;

import com.asisge.consultifybackend.actividades.aplicacion.dto.ActividadDto;
import com.asisge.consultifybackend.actividades.dominio.modelo.Actividad;
import org.springframework.scheduling.annotation.Async;

public interface NotificadorActividad {

    @Async
    void notificarResponsableActividad(Actividad actividad, ActividadDto dto, String usuarioEnSesion);
}
