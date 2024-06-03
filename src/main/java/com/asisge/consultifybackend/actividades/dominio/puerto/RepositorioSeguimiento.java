package com.asisge.consultifybackend.actividades.dominio.puerto;

import com.asisge.consultifybackend.actividades.dominio.modelo.Seguimiento;

import java.util.List;

public interface RepositorioSeguimiento {

    List<Seguimiento> obtenerPorIdActividad(Long idActividad);

    Seguimiento crearSeguimiento(Seguimiento seguimiento);
}
