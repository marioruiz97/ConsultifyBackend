package com.asisge.consultifybackend.proyectos.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioProyecto;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.convertidor.ConvertidorProyecto;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadProyecto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RepositorioProyectoJPA extends JpaRepository<EntidadProyecto, Long>, RepositorioProyecto {

    // metodos JPA y JPQL
    @Query("SELECT p FROM EntidadProyecto p JOIN p.miembros u WHERE u.idUsuario = :idUsuario")
    List<EntidadProyecto> findProyectosByIdUsuario(@Param("idUsuario") Long idUsuario);

    // metodos propios
    @Override
    default List<Proyecto> obtenerTodos() {
        return findAll().stream().map(ConvertidorProyecto::aDominio).toList();
    }

    @Override
    @Transactional(readOnly = true)
    default List<Proyecto> obtenerMisProyectos(Long idUsuario) {
        List<EntidadProyecto> misProyectos = findProyectosByIdUsuario(idUsuario);
        return misProyectos.stream().map(ConvertidorProyecto::aDominio).toList();
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
        EntidadProyecto actual = findById(idProyecto).orElse(null);

        actual = ConvertidorProyecto.aActualizarEntidad(actual, proyecto);
        assert actual != null;
        return ConvertidorProyecto.aDominio(save(actual));
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
