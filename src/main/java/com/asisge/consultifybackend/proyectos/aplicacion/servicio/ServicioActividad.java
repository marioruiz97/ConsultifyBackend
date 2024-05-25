package com.asisge.consultifybackend.proyectos.aplicacion.servicio;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.ActividadDto;
import com.asisge.consultifybackend.proyectos.aplicacion.dto.CambioEstadoActividadDto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;

public interface ServicioActividad {

    Actividad obtenerActividadPorId(Long idProyecto, Long idActividad);

    Actividad crearActividad(Long idProyecto, ActividadDto nuevaActividad);

    Actividad editarActividad(Long idProyecto, Long idActividad, ActividadDto nuevaActividad);

    Actividad cambiarEstadoActividad(Long idProyecto, Long idActividad, CambioEstadoActividadDto estadoActividadDto);

    void eliminarActividad(Long idProyecto, Long idActividad);

}
