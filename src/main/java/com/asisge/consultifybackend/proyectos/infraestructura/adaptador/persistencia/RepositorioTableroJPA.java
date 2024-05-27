package com.asisge.consultifybackend.proyectos.infraestructura.adaptador.persistencia;

import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioTablero;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadProyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.convertidor.ConvertidorUsuario;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RepositorioTableroJPA extends JpaRepository<EntidadProyecto, Long>, RepositorioTablero {

    // metodos JPQL
    @Query("SELECT u FROM EntidadUsuario u WHERE u NOT IN (SELECT mp FROM EntidadProyecto p JOIN p.miembros mp WHERE p.idProyecto = :idProyecto)")
    List<EntidadUsuario> findPosiblesMiembros(@Param("idProyecto") Long idProyecto);

    @Query("SELECT mp FROM EntidadProyecto p JOIN p.miembros mp WHERE p.idProyecto = :idProyecto")
    List<EntidadUsuario> findMiembrosProyecto(@Param("idProyecto") Long idProyecto);


    // metodos propios
    @Transactional(readOnly = true)
    @Override
    default List<UsuarioAutenticado> obtenerPosiblesMiembros(Long idProyecto) {
        List<EntidadUsuario> usuarios = this.findPosiblesMiembros(idProyecto);
        return usuarios.stream().map(ConvertidorUsuario::aDominio).toList();
    }

    @Override
    default void agregarMiembroAlProyecto(Long idProyecto, Usuario usuario) {
        EntidadProyecto proyecto = findById(idProyecto).orElseThrow();
        EntidadUsuario nuevoMiembro = new EntidadUsuario(usuario.getIdUsuario());
        proyecto.agregarMiembro(nuevoMiembro);
        save(proyecto);
    }

    @Override
    default void quitarMiembroProyecto(Long idProyecto, Long idMiembro) {
        EntidadProyecto proyecto = findById(idProyecto).orElseThrow();
        proyecto.quitarMiembro(idMiembro);
        save(proyecto);
    }

    @Transactional(readOnly = true)
    @Override
    default List<UsuarioAutenticado> obtenerMiembrosProyecto(Long idProyecto) {
        List<EntidadUsuario> usuarios = this.findMiembrosProyecto(idProyecto);
        return usuarios.stream().map(ConvertidorUsuario::aDominio).toList();
    }

}
