package com.asisge.consultifybackend.actividades.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.actividades.dominio.modelo.Seguimiento;
import com.asisge.consultifybackend.actividades.dominio.puerto.RepositorioSeguimiento;
import com.asisge.consultifybackend.actividades.infraestructura.adaptador.convertidor.ConvertidorSeguimiento;
import com.asisge.consultifybackend.actividades.infraestructura.adaptador.entidad.EntidadSeguimiento;
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
    default Seguimiento crearSeguimiento(Seguimiento seguimiento) {
        EntidadSeguimiento entidad = ConvertidorSeguimiento.aEntidad(seguimiento);
        return ConvertidorSeguimiento.aDominio(save(entidad));
    }
}
