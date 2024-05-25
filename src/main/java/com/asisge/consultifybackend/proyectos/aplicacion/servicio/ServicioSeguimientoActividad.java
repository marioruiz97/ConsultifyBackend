package com.asisge.consultifybackend.proyectos.aplicacion.servicio;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.NuevoSeguimientoDto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Seguimiento;

import java.util.List;

public interface ServicioSeguimientoActividad {

    List<Seguimiento> obtenerSeguimientos(Long idActividad);

    Seguimiento crearSeguimiento(Long idActividad, NuevoSeguimientoDto nuevoSeguimiento);
}
