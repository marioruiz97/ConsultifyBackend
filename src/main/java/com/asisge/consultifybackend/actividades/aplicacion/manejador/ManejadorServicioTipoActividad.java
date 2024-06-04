package com.asisge.consultifybackend.actividades.aplicacion.manejador;

import com.asisge.consultifybackend.actividades.aplicacion.servicio.ServicioTipoActividad;
import com.asisge.consultifybackend.actividades.dominio.modelo.TipoActividad;
import com.asisge.consultifybackend.actividades.dominio.puerto.RepositorioTipoActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManejadorServicioTipoActividad implements ServicioTipoActividad {

    private final RepositorioTipoActividad repositorioTipoActividad;

    @Autowired
    public ManejadorServicioTipoActividad(RepositorioTipoActividad repositorioTipoActividad) {
        this.repositorioTipoActividad = repositorioTipoActividad;
    }

    @Override
    public List<TipoActividad> obtenerTodos() {
        return repositorioTipoActividad.obtenerTodos();
    }

    @Override
    public TipoActividad crearTipoActividad(TipoActividad tipoActividad) {
        return repositorioTipoActividad.crearTipoActividad(tipoActividad);
    }

    @Override
    public TipoActividad editarTipoActividad(TipoActividad tipoActividad) {
        return repositorioTipoActividad.editarTipoActividad(tipoActividad);
    }

    @Secured("ROLE_ADMIN")
    @Override
    public void eliminarTipoActividad(Long idTipo) {
        repositorioTipoActividad.eliminarTipoActividad(idTipo);
    }
}
