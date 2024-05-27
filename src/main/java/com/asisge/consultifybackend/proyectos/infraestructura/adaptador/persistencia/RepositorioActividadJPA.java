package com.asisge.consultifybackend.proyectos.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioActividad;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.convertidor.ConvertidorActividad;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadActividad;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositorioActividadJPA extends JpaRepository<EntidadActividad, Long>, RepositorioActividad {


    // metodos JPQL
    @Query("SELECT a FROM EntidadActividad a JOIN a.proyecto p WHERE p.idProyecto= :idProyecto")
    List<EntidadActividad> findByIdProyecto(@Param("idProyecto") Long idProyecto);

    @Query("SELECT a FROM EntidadActividad a JOIN a.responsable r WHERE a.estado != 'COMPLETADA' AND a.fechaCierreEsperado <= :fecha AND " +
            "r.nombreUsuario= :usernameOCorreo OR r.correo= :usernameOCorreo")
    List<EntidadActividad> findByUsernameOCorreo(@Param("usernameOCorreo") String usernameOCorreo, @Param("fecha") LocalDate fecha);


    // metodos propios
    @Override
    default Actividad obtenerActividadPorId(Long idActividad) {
        EntidadActividad entidad = findById(idActividad).orElseThrow(() -> new EntityNotFoundException("No se encontró la actividad en base de datos"));
        return ConvertidorActividad.aDominio(entidad);
    }

    @Transactional(readOnly = true)
    @Override
    default List<Actividad> obtenerActividadesPorProyecto(Proyecto proyecto) {
        List<EntidadActividad> actividades = findByIdProyecto(proyecto.getIdProyecto());
        return actividades.stream().map(ConvertidorActividad::aDominio).toList();
    }

    @Transactional(readOnly = true)
    @Override
    default List<Actividad> obtenerMisActividades(String usernameOCorreo) {
        LocalDate fecha = LocalDate.now();
        fecha = fecha.plusDays(7);

        List<EntidadActividad> actividades = findByUsernameOCorreo(usernameOCorreo, fecha);
        return actividades.stream().map(ConvertidorActividad::aDominio).toList();
    }


    @Override
    default Actividad crearActividad(Actividad actividad) {
        EntidadActividad entidad = ConvertidorActividad.aEntidad(actividad);
        return ConvertidorActividad.aDominio(save(entidad));
    }

    @Override
    default Actividad editarActividad(Long idActividad, Actividad actividad) {
        EntidadActividad actual = findById(idActividad).orElseThrow();
        actual = ConvertidorActividad.aActualizarEntidad(actual, actividad);
        return ConvertidorActividad.aDominio(save(actual));
    }

    @Override
    default void eliminarActividad(Long idActividad) {
        deleteById(idActividad);
    }
}
