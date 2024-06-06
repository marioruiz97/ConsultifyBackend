package com.asisge.consultifybackend.proyectos.aplicacion.manejador;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.MiembroDto;
import com.asisge.consultifybackend.proyectos.aplicacion.dto.TableroProyecto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioTablero;
import com.asisge.consultifybackend.actividades.dominio.modelo.Actividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.actividades.dominio.puerto.RepositorioActividad;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioProyecto;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioTablero;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ManejadorServicioTablero implements ServicioTablero {

    private final RepositorioProyecto repositorioProyecto;

    private final RepositorioTablero repositorioTablero;

    private final RepositorioActividad repositorioActividad;
    private final Logger logger = LoggerFactory.getLogger(ManejadorServicioTablero.class);


    public ManejadorServicioTablero(RepositorioProyecto repositorioProyecto,
                                    RepositorioActividad repositorioActividad,
                                    RepositorioTablero repositorioTablero
    ) {
        this.repositorioProyecto = repositorioProyecto;
        this.repositorioActividad = repositorioActividad;
        this.repositorioTablero = repositorioTablero;
    }

    @Override
    public TableroProyecto obtenerProyectoPorId(Long idProyecto) {
        Proyecto proyecto = repositorioProyecto.obtenerProyectoPorId(idProyecto);

        String mensaje = Mensajes.getString("tableros.info.obtener.tablero.proyecto", proyecto.getNombreProyecto());
        logger.info(mensaje, proyecto);

        List<Actividad> actividades = repositorioActividad.obtenerActividadesPorProyecto(proyecto);

        mensaje = Mensajes.getString("tableros.info.obtener.actividades.proyecto", proyecto.getNombreProyecto(), actividades.size());
        logger.info(mensaje);

        return new TableroProyecto(proyecto, actividades);
    }

    @Override
    public List<UsuarioAutenticado> obtenerPosiblesMiembros(Long idProyecto) {
        List<UsuarioAutenticado> posiblesMiembros = repositorioTablero.obtenerPosiblesMiembros(idProyecto);
        posiblesMiembros.forEach(UsuarioAutenticado::limpiarContrasena);
        return posiblesMiembros;
    }

    @Override
    public UsuarioAutenticado agregarMiembroAlProyecto(Long idProyecto, MiembroDto miembroDto) {
        if (!miembroDto.validarDto()) {
            String mensaje = Mensajes.getString("tableros.error.nuevo.miembro.invalido");
            logger.error(mensaje, miembroDto);
            throw new IllegalArgumentException(mensaje);
        }
        Usuario usuario = miembroDto.getUsuario();
        repositorioTablero.agregarMiembroAlProyecto(idProyecto, usuario);

        String mensaje = Mensajes.getString("tableros.info.nuevo.miembro.agregado", idProyecto);
        logger.info(mensaje, usuario);

        return new UsuarioAutenticado(usuario, miembroDto.getNombreUsuario(), null, null, null, null, null, null);
    }

    @Override
    public List<UsuarioAutenticado> quitarMiembroProyecto(Long idProyecto, Long idMiembro) {
        String mensaje = Mensajes.getString("tableros.info.eliminar.miembro.accion", idMiembro, idProyecto);
        logger.info(mensaje);

        repositorioTablero.quitarMiembroProyecto(idProyecto, idMiembro);
        mensaje = Mensajes.getString("tableros.info.eliminar.miembro.exitoso", idMiembro, idProyecto);
        logger.info(mensaje);

        return repositorioTablero.obtenerMiembrosProyecto(idProyecto);
    }
}
