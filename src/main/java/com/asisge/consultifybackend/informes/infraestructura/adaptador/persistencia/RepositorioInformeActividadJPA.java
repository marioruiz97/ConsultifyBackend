package com.asisge.consultifybackend.informes.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.informes.dominio.puerto.RepositorioInformeActividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.EstadoActividad;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RepositorioInformeActividadJPA extends JpaRepository<EntidadActividad, Long>, RepositorioInformeActividad {

    // metodos JPQL
    @Query("SELECT COUNT(a) FROM EntidadActividad a WHERE a.proyecto.idProyecto = :idProyecto")
    int findTotalActividades(@Param("idProyecto") Long idProyecto);

    @Query("SELECT COUNT(a) FROM EntidadActividad a WHERE a.proyecto.idProyecto = :idProyecto and a.estado != :estadoCompletado and a.fechaCierreEsperado <= :fecha")
    int findActividadesPorVencer(@Param("idProyecto") Long idProyecto, @Param("fecha") LocalDate fecha, @Param("estadoCompletado") EstadoActividad estadoCompletado);

    @Query("SELECT COUNT(a) FROM EntidadActividad a WHERE a.proyecto.idProyecto = :idProyecto and a.estado = :estado")
    int findActividadesByEstado(@Param("idProyecto") Long idProyecto, @Param("estado") EstadoActividad estado);


    // metodos propios

    @Override
    default int obtenerTotalActividades(Long idProyecto) {
        return findTotalActividades(idProyecto);
    }

    @Override
    default int obtenerActividadesPorVencer(Long idProyecto) {
        LocalDate fecha = LocalDate.now();
        LocalDate fechaFutura = fecha.plusDays(7); // obtener la fecha de la semana siguiente
        return findActividadesPorVencer(idProyecto, fechaFutura, EstadoActividad.COMPLETADA);
    }

    @Override
    default int obtenerActividadesCompletas(Long idProyecto) {
        return findActividadesByEstado(idProyecto, EstadoActividad.COMPLETADA);
    }

    @Override
    default int obtenerActividadesPorHacer(Long idProyecto) {
        return findActividadesByEstado(idProyecto, EstadoActividad.POR_HACER);
    }

    @Override
    default int obtenerActividadesEnProgreso(Long idProyecto) {
        return findActividadesByEstado(idProyecto, EstadoActividad.EN_PROGRESO);
    }

    @Override
    default int obtenerActividadesEnRevision(Long idProyecto) {
        return findActividadesByEstado(idProyecto, EstadoActividad.EN_REVISION);
    }

}
