package com.asisge.consultifybackend.proyectos.aplicacion.manejador;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.ActividadDto;
import com.asisge.consultifybackend.proyectos.aplicacion.mapeador.MapeadorActividad;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioActividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.EstadoActividad;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioActividad;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioProyecto;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import com.asisge.consultifybackend.utilidad.dominio.excepcion.AccionNoPermitidaException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManejadorServicioActividad implements ServicioActividad {

    private final Logger logger = LoggerFactory.getLogger(ManejadorServicioActividad.class);

    private final RepositorioProyecto repositorioProyecto;
    private final RepositorioActividad repositorioActividad;
    private final MapeadorActividad mapeadorActividad;

    @Autowired
    public ManejadorServicioActividad(RepositorioProyecto repositorioProyecto,
                                      RepositorioActividad repositorioActividad,
                                      MapeadorActividad mapeadorActividad) {
        this.repositorioProyecto = repositorioProyecto;
        this.repositorioActividad = repositorioActividad;
        this.mapeadorActividad = mapeadorActividad;
    }

    @Override
    public Actividad obtenerActividadPorId(Long idProyecto, Long idActividad) {
        validarProyecto(idProyecto);

        return repositorioActividad.obtenerActividadPorId(idActividad);
    }

    @Override
    public Actividad crearActividad(Long idProyecto, ActividadDto nuevaActividad) {
        validarProyecto(idProyecto);

        nuevaActividad.setEstado(EstadoActividad.POR_HACER);
        Actividad actividad = mapeadorActividad.aActividad(nuevaActividad, idProyecto);

        String mensaje = Mensajes.getString("actividad.info.crear.actividad");
        logger.info(mensaje, actividad);

        return repositorioActividad.crearActividad(actividad);
    }


    @Override
    public Actividad editarActividad(Long idProyecto, Long idActividad, ActividadDto editarActividad) {
        validarProyecto(idProyecto);
        if (editarActividad.getId() == null || !idActividad.equals(editarActividad.getId()))
            throw new AccionNoPermitidaException(
                    Mensajes.getString("actividad.error.id.actividad.no.coincide", idActividad));

        Actividad actividad = mapeadorActividad.aActividad(editarActividad, idProyecto);

        String mensaje = Mensajes.getString("actividad.info.editar.actividad", idActividad);
        logger.info(mensaje, actividad);

        return repositorioActividad.editarActividad(idActividad, actividad);
    }

    @Override
    public void eliminarActividad(Long idProyecto, Long idActividad) {
        validarProyecto(idProyecto);

        repositorioActividad.eliminarActividad(idActividad);
        String mensaje = Mensajes.getString("actividad.info.eliminar.actividad.exito", idActividad);
        logger.info(mensaje);
    }

    private void validarProyecto(Long idProyecto) {
        if (!repositorioProyecto.existeProyectoPorId(idProyecto)) {
            String mensaje = Mensajes.getString("actividad.error.proyecto.no.encontrado", idProyecto);
            logger.error(mensaje);
            throw new EntityNotFoundException(mensaje);
        }
    }


}
