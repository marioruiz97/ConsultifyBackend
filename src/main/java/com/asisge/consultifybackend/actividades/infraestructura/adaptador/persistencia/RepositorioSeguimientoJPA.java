package com.asisge.consultifybackend.actividades.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.actividades.dominio.modelo.Seguimiento;
import com.asisge.consultifybackend.actividades.dominio.puerto.RepositorioSeguimiento;
import com.asisge.consultifybackend.actividades.infraestructura.adaptador.convertidor.ConvertidorSeguimiento;
import com.asisge.consultifybackend.actividades.infraestructura.adaptador.entidad.EntidadSeguimiento;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioSeguimientoJPA extends JpaRepository<EntidadSeguimiento, Long>, RepositorioSeguimiento {

    // metodos JPQL
    @Query("SELECT s FROM EntidadSeguimiento s JOIN s.actividad a WHERE a.id= :idActividad")
    List<EntidadSeguimiento> findByIdActividad(@Param("idActividad") Long idActividad);


    // metodos propios
    @Override
    default List<Seguimiento> obtenerPorIdActividad(Long idActividad) {
        List<EntidadSeguimiento> seguimientos = findByIdActividad(idActividad);
        return seguimientos.stream().map(ConvertidorSeguimiento::aDominio).toList();
    }


    @Override
    default Seguimiento buscarPorIdSeguimiento(Long idSeguimiento) {
        EntidadSeguimiento entidad = findById(idSeguimiento).orElseThrow(EntityNotFoundException::new);
        return ConvertidorSeguimiento.aDominio(entidad);
    }

    @Override
    default Seguimiento crearSeguimiento(Seguimiento seguimiento) {
        EntidadSeguimiento entidad = ConvertidorSeguimiento.aEntidad(seguimiento);
        return ConvertidorSeguimiento.aDominio(save(entidad));
    }

    @Override
    default Seguimiento editarSeguimiento(Seguimiento seguimientoEditado) {
        EntidadSeguimiento entidad = this.findById(seguimientoEditado.getIdSeguimiento()).orElse(null);
        if (entidad == null)
            throw new EntityNotFoundException(Mensajes.getString("seguimientos.error.id.seguimiento.no.existe", seguimientoEditado.getIdSeguimiento()));

        entidad.setComentarios(seguimientoEditado.getComentarios());

        return ConvertidorSeguimiento.aDominio(save(entidad));
    }

    @Override
    default void eliminarSeguimiento(Long idSeguimiento) {
        deleteById(idSeguimiento);
    }

}
