package com.asisge.consultifybackend.proyectos.aplicacion.servicio;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.ActividadDto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;

public interface ServicioActividad {

    Actividad crearActividad(Long idProyecto, ActividadDto nuevaActividad);

    Actividad editarActividad(Long idProyecto, Long idActividad, ActividadDto nuevaActividad);

    void eliminarActividad(Long idProyecto, Long idActividad);
}
