package com.asisge.consultifybackend.actividades.dominio.puerto;

import com.asisge.consultifybackend.actividades.dominio.modelo.TipoActividad;

import java.util.List;

public interface RepositorioTipoActividad {

    List<TipoActividad> obtenerTodos();

    TipoActividad crearTipoActividad(TipoActividad tipoActividad);

    TipoActividad editarTipoActividad(TipoActividad tipoActividad);

    void eliminarTipoActividad(Long idTipo);

}
