package com.asisge.consultifybackend.proyectos.aplicacion.manejador;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.ProyectoDto;
import com.asisge.consultifybackend.proyectos.aplicacion.mapeador.MapeadorProyecto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioProyecto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioProyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.dominio.puerto.RepositorioUsuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManejadorServicioProyecto implements ServicioProyecto {

    private final RepositorioProyecto repositorioProyecto;

    private final RepositorioUsuario repositorioUsuario;
    private final MapeadorProyecto mapeadorProyecto;

    public ManejadorServicioProyecto(RepositorioProyecto repositorioProyecto, RepositorioUsuario repositorioUsuario, MapeadorProyecto mapeadorProyecto) {
        this.repositorioProyecto = repositorioProyecto;
        this.repositorioUsuario = repositorioUsuario;
        this.mapeadorProyecto = mapeadorProyecto;
    }

    @Override
    public List<Proyecto> obtenerTodos() {
        return repositorioProyecto.obtenerTodos();
    }

    @Override
    public Proyecto obtenerProyectoPorId(Long idProyecto) {
        return repositorioProyecto.obtenerProyectoPorId(idProyecto);
    }

    @Override
    public Proyecto crearProyecto(ProyectoDto dto) {
        Proyecto proyecto = mapeadorProyecto.aProyecto(dto);
        proyecto.validarProyecto();
        UsuarioAutenticado usuario = repositorioUsuario.buscarPorCorreoOUsername(dto.getCreadoPor());
        proyecto.agregarMiembro(usuario);
        return repositorioProyecto.crearProyecto(proyecto);
    }

    @Override
    public Proyecto editarProyecto(Long idProyecto, Proyecto proyecto) {
        if (proyecto.getIdProyecto() == null || !proyecto.getIdProyecto().equals(idProyecto))
            throw new IllegalArgumentException("El id del proyecto es null o no corresponde al del objeto enviado en la peticion");
        if (!repositorioProyecto.existeProyectoPorId(idProyecto))
            throw new EntityNotFoundException("No se encontro el proyecto en la base de datos");
        proyecto.validarProyecto();
        return repositorioProyecto.editarProyecto(idProyecto, proyecto);
    }

    @Secured("ROLE_ADMIN")
    @Override
    public Boolean eliminarProyecto(Long idProyecto) {
        if (!repositorioProyecto.existeProyectoPorId(idProyecto))
            return Boolean.FALSE;
        repositorioProyecto.eliminarProyecto(idProyecto);
        return Boolean.TRUE;
    }
}
