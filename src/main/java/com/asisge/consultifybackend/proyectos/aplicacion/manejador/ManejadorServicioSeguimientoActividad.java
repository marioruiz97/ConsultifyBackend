package com.asisge.consultifybackend.proyectos.aplicacion.manejador;

import com.asisge.consultifybackend.proyectos.aplicacion.dto.NuevoSeguimientoDto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguimientoActividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Seguimiento;
import com.asisge.consultifybackend.proyectos.dominio.puerto.RepositorioSeguimiento;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.puerto.RepositorioUsuario;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import com.asisge.consultifybackend.utilidad.dominio.excepcion.AccionNoPermitidaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Service
public class ManejadorServicioSeguimientoActividad implements ServicioSeguimientoActividad {

    private static final Logger logger = LoggerFactory.getLogger(ManejadorServicioSeguimientoActividad.class);
    private final RepositorioSeguimiento repositorioSeguimiento;
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ManejadorServicioSeguimientoActividad(RepositorioSeguimiento repositorioSeguimiento, RepositorioUsuario repositorioUsuario) {
        this.repositorioSeguimiento = repositorioSeguimiento;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public List<Seguimiento> obtenerSeguimientos(Long idActividad) {
        return repositorioSeguimiento.obtenerPorIdActividad(idActividad);
    }

    @Override
    public Seguimiento crearSeguimiento(Long idActividad, NuevoSeguimientoDto nuevoSeguimiento) {
        if (!idActividad.equals(nuevoSeguimiento.getActividad().getId()))
            throw new AccionNoPermitidaException(Mensajes.getString("seguimientos.error.id.actividad.no.coincide", idActividad));

        Seguimiento seguimiento = nuevoSeguimiento(nuevoSeguimiento);

        String mensaje = Mensajes.getString("seguimientos.info.crear.nuevo.seguimiento", idActividad);
        logger.info(mensaje, seguimiento);

        return repositorioSeguimiento.crearSeguimiento(seguimiento);
    }

    private Seguimiento nuevoSeguimiento(NuevoSeguimientoDto nuevoSeguimiento) {
        Usuario usuario = repositorioUsuario.buscarPorCorreoOUsername(nuevoSeguimiento.getUsername()).getUsuario();
        return new Seguimiento(
                null,
                nuevoSeguimiento.getActividad(),
                usuario,
                LocalDateTime.now(ZoneId.systemDefault()),
                nuevoSeguimiento.getComentarios()
        );
    }
}
