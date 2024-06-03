package com.asisge.consultifybackend.actividades.aplicacion.servicio;

import com.asisge.consultifybackend.actividades.aplicacion.dto.ActividadDto;
import com.asisge.consultifybackend.actividades.aplicacion.dto.CambioEstadoActividadDto;
import com.asisge.consultifybackend.actividades.dominio.modelo.Actividad;

import java.util.List;

public interface ServicioActividad {

    Actividad obtenerActividadPorId(Long idProyecto, Long idActividad);

    Actividad crearActividad(Long idProyecto, ActividadDto nuevaActividad);

    Actividad editarActividad(Long idProyecto, Long idActividad, ActividadDto nuevaActividad);

    Actividad cambiarEstadoActividad(Long idProyecto, Long idActividad, CambioEstadoActividadDto estadoActividadDto);

    void eliminarActividad(Long idProyecto, Long idActividad);

    List<Actividad> obtenerMisActividades(String usernameOCorreo);

}
