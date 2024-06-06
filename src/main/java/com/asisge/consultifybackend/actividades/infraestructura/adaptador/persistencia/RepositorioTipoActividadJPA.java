package com.asisge.consultifybackend.actividades.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.actividades.dominio.modelo.TipoActividad;
import com.asisge.consultifybackend.actividades.dominio.puerto.RepositorioTipoActividad;
import com.asisge.consultifybackend.actividades.infraestructura.adaptador.convertidor.ConvertidorTipoActividad;
import com.asisge.consultifybackend.actividades.infraestructura.adaptador.entidad.EntidadTipoActividad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioTipoActividadJPA extends JpaRepository<EntidadTipoActividad, Long>, RepositorioTipoActividad {

    @Override
    default List<TipoActividad> obtenerTodos() {
        List<EntidadTipoActividad> tiposActividad = this.findAll();
        return tiposActividad.stream().map(ConvertidorTipoActividad::aDominio).toList();
    }

    @Override
    default TipoActividad crearTipoActividad(TipoActividad tipoActividad) {
        EntidadTipoActividad entidad = new EntidadTipoActividad();
        entidad.setNombre(tipoActividad.getNombre());

        return ConvertidorTipoActividad.aDominio(this.save(entidad));
    }

    @Override
    default TipoActividad editarTipoActividad(TipoActividad tipoActividad) {
        EntidadTipoActividad entidad = new EntidadTipoActividad(
                tipoActividad.getIdTipo(),
                tipoActividad.getNombre()
        );

        return ConvertidorTipoActividad.aDominio(this.save(entidad));
    }

    @Override
    default void eliminarTipoActividad(Long idTipo) {
        this.deleteById(idTipo);
    }

}
