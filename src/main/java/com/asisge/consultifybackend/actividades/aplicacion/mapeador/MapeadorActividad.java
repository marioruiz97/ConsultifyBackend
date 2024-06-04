package com.asisge.consultifybackend.actividades.aplicacion.mapeador;

import com.asisge.consultifybackend.actividades.aplicacion.dto.ActividadDto;
import com.asisge.consultifybackend.actividades.dominio.modelo.Actividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import org.springframework.stereotype.Component;

@Component
public class MapeadorActividad {
    public Actividad aActividad(ActividadDto nuevaActividad, Long idProyecto) {

        return new Actividad(
                nuevaActividad.getId(),
                nuevaActividad.getNombre(),
                nuevaActividad.getDescripcion(),
                new Proyecto(idProyecto),
                nuevaActividad.getEstado(),
                nuevaActividad.getFechaCierreEsperado(),
                nuevaActividad.getTipoActividad(),
                nuevaActividad.getResponsable(),
                nuevaActividad.getFechaCompletada()
        );
    }
}
