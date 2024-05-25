package com.asisge.consultifybackend.proyectos.dominio.puerto;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Seguimiento;

import java.util.List;

public interface RepositorioSeguimiento {

    List<Seguimiento> obtenerPorIdActividad(Long idActividad);

    Seguimiento crearSeguimiento(Seguimiento seguimiento);
}
