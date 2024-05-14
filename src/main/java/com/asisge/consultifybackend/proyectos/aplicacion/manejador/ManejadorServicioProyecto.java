package com.asisge.consultifybackend.proyectos.aplicacion.manejador;


import com.asisge.consultifybackend.proyectos.aplicacion.dto.ProyectoDto;
import com.asisge.consultifybackend.proyectos.aplicacion.mapeador.MapeadorProyecto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioProyecto;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioProyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.dominio.puerto.RepositorioUsuario;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ManejadorServicioProyecto implements ServicioProyecto {

    private final RepositorioProyecto repositorioProyecto;
    private final RepositorioUsuario repositorioUsuario;
    private final MapeadorProyecto mapeadorProyecto;
    private final Logger logger = LoggerFactory.getLogger(ManejadorServicioProyecto.class);

    @Autowired
    public ManejadorServicioProyecto(RepositorioProyecto repositorioProyecto,
                                     RepositorioUsuario repositorioUsuario,
                                     MapeadorProyecto mapeadorProyecto) {
        this.repositorioProyecto = repositorioProyecto;
        this.repositorioUsuario = repositorioUsuario;
        this.mapeadorProyecto = mapeadorProyecto;
    }

    @Override
    public List<Proyecto> obtenerTodos() {
        return repositorioProyecto.obtenerTodos();
    }

    @Override
    public List<Proyecto> obtenerMisProyectos(@NotBlank String usernameOCorreo) {
        UsuarioAutenticado usuario = repositorioUsuario.buscarPorCorreoOUsername(usernameOCorreo);

        String mensaje = Mensajes.getString("mis.proyectos.info.buscar", usernameOCorreo);
        logger.info(mensaje);
        return repositorioProyecto.obtenerMisProyectos(usuario.getUsuario().getIdUsuario());
    }

    @Override
    public Proyecto crearProyecto(ProyectoDto dto) {
        Proyecto proyecto = mapeadorProyecto.aProyecto(dto);
        proyecto.validarProyecto();
        UsuarioAutenticado usuario = repositorioUsuario.buscarPorCorreoOUsername(dto.getCreadoPor());
        proyecto.agregarMiembro(usuario);

        String mensaje = Mensajes.getString("proyectos.info.crear.proyecto", usuario.getNombreUsuario(), proyecto.getNombreProyecto());
        logger.info(mensaje);

        return repositorioProyecto.crearProyecto(proyecto);
    }

    @Override
    public Proyecto editarProyecto(Long idProyecto, ProyectoDto proyectoDto) {
        Proyecto proyecto = mapeadorProyecto.aProyecto(proyectoDto);
        if (proyecto.getIdProyecto() == null || !proyecto.getIdProyecto().equals(idProyecto))
            throw new IllegalArgumentException(Mensajes.getString("proyectos.error.id.proyecto.no.coincide"));
        if (!repositorioProyecto.existeProyectoPorId(idProyecto))
            throw new EntityNotFoundException(Mensajes.getString("proyectos.error.proyecto.no.encontrado", idProyecto));
        proyecto.validarProyecto();

        String mensaje = Mensajes.getString("proyectos.info.editar.proyecto", proyecto.getIdProyecto());
        logger.info(mensaje);

        return repositorioProyecto.editarProyecto(idProyecto, proyecto);
    }

    @Secured("ROLE_ADMIN")
    @Override
    public Boolean eliminarProyecto(Long idProyecto) {
        if (!repositorioProyecto.existeProyectoPorId(idProyecto))
            return Boolean.FALSE;
        repositorioProyecto.eliminarProyecto(idProyecto);

        String mensaje = Mensajes.getString("proyectos.info.eliminar.proyecto.exito", idProyecto);
        logger.info(mensaje);

        return Boolean.TRUE;
    }
}
