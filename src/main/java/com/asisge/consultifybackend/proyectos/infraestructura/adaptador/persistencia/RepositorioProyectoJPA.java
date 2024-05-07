package com.asisge.consultifybackend.proyectos.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioProyecto;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.convertidor.ConvertidorProyecto;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadProyecto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RepositorioProyectoJPA extends JpaRepository<EntidadProyecto, Long>, RepositorioProyecto {

    @Override
    default List<Proyecto> obtenerTodos() {
        return findAll().stream().map(ConvertidorProyecto::aDominio).toList();
    }

    @Override
    default Proyecto obtenerProyectoPorId(Long idProyecto) {
        EntidadProyecto entidad = findById(idProyecto).orElseThrow(() -> new EntityNotFoundException("No se encontró el proyecto en base de datos"));
        return ConvertidorProyecto.aDominio(entidad);
    }

    @Transactional
    @Override
    default Proyecto crearProyecto(Proyecto proyecto) {
        EntidadProyecto entidad = ConvertidorProyecto.aEntidad(proyecto);
        return ConvertidorProyecto.aDominio(saveAndFlush(entidad));
    }

    @Override
    default Proyecto editarProyecto(Long idProyecto, Proyecto proyecto) {
        EntidadProyecto entidad = ConvertidorProyecto.aEntidad(proyecto);
        entidad.setIdProyecto(idProyecto);
        return ConvertidorProyecto.aDominio(save(entidad));
    }

    @Override
    default void eliminarProyecto(Long idProyecto) {
        deleteById(idProyecto);
    }

    @Override
    default boolean existeProyectoPorId(Long idProyecto) {
        return existsById(idProyecto);
    }
}
