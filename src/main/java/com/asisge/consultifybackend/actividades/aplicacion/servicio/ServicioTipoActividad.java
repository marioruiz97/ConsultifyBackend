package com.asisge.consultifybackend.actividades.aplicacion.servicio;

import com.asisge.consultifybackend.actividades.dominio.modelo.TipoActividad;

import java.util.List;

public interface ServicioTipoActividad {

    List<TipoActividad> obtenerTodos();

    TipoActividad crearTipoActividad(TipoActividad tipoActividad);

    TipoActividad editarTipoActividad(TipoActividad tipoActividad);

    void eliminarTipoActividad(Long idTipo);

}
