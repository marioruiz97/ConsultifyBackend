package com.asisge.consultifybackend.informes.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.informes.dominio.modelo.InformeProyecto;
import com.asisge.consultifybackend.informes.dominio.puerto.RepositorioInformeProyecto;
import com.asisge.consultifybackend.informes.infraestructura.adaptador.convertidor.ConvertidorInforme;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadProyecto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioInformeProyectoJPA extends JpaRepository<EntidadProyecto, Long>, RepositorioInformeProyecto {

    // metodos JPQL
    @Query("SELECT p " +
            "FROM EntidadProyecto p JOIN p.miembros m WHERE m.correo = :username OR m.nombreUsuario = :username")
    List<EntidadProyecto> findEncabezadosByNombreUsuario(@Param("username") String username);


    // metodos propios
    @Override
    default List<InformeProyecto> buscarEncabezados() {
        List<EntidadProyecto> entidades = this.findAll();
        return entidades.stream().map(ConvertidorInforme::aInfoProyecto).toList();
    }

    @Override
    default List<InformeProyecto> buscarMisEncabezados(String usernameOCorreo) {
        List<EntidadProyecto> entidades = this.findEncabezadosByNombreUsuario(usernameOCorreo);
        return entidades.stream().map(ConvertidorInforme::aInfoProyecto).toList();
    }

    @Override
    default InformeProyecto obtenerInformeProyecto(Long idProyecto) {
        EntidadProyecto entidadProyecto = this.findById(idProyecto).orElseThrow(() -> new EntityNotFoundException("No se encontró el proyecto en base de datos"));
        return ConvertidorInforme.aInfoProyecto(entidadProyecto);
    }

}
