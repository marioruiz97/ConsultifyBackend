package com.asisge.consultifybackend.actividades.aplicacion.servicio;

import com.asisge.consultifybackend.actividades.aplicacion.dto.NuevoSeguimientoDto;
import com.asisge.consultifybackend.actividades.dominio.modelo.Seguimiento;

import java.util.List;

public interface ServicioSeguimientoActividad {

    List<Seguimiento> obtenerSeguimientos(Long idActividad);

    Seguimiento crearSeguimiento(Long idActividad, NuevoSeguimientoDto nuevoSeguimiento);
}
