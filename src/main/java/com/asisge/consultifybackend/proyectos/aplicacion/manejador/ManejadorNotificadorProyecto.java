package com.asisge.consultifybackend.proyectos.aplicacion.manejador;

import com.asisge.consultifybackend.notificaciones.aplicacion.servicio.ServicioNotificacion;
import com.asisge.consultifybackend.notificaciones.dominio.modelo.Notificacion;
import com.asisge.consultifybackend.notificaciones.dominio.modelo.TipoNotificacion;
import com.asisge.consultifybackend.actividades.aplicacion.dto.ActividadDto;
import com.asisge.consultifybackend.actividades.aplicacion.servicio.NotificadorActividad;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.NotificadorProyecto;
import com.asisge.consultifybackend.actividades.dominio.modelo.Actividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import com.asisge.consultifybackend.usuarios.dominio.puerto.RepositorioUsuario;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.ServicioCorreo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ManejadorNotificadorProyecto implements NotificadorActividad, NotificadorProyecto {

    private final Logger logger = LoggerFactory.getLogger(ManejadorNotificadorProyecto.class);

    private final RepositorioUsuario repositorioUsuario;
    private final ServicioCorreo servicioCorreo;
    private final ServicioNotificacion servicioNotificacion;

    @Autowired
    public ManejadorNotificadorProyecto(RepositorioUsuario repositorioUsuario,
                                        ServicioCorreo servicioCorreo,
                                        ServicioNotificacion servicioNotificacion) {
        this.repositorioUsuario = repositorioUsuario;
        this.servicioCorreo = servicioCorreo;
        this.servicioNotificacion = servicioNotificacion;
    }

    @Async
    @Override
    public void notificarResponsableActividad(Actividad actividad, ActividadDto dto, String username) {

        try {
            Usuario creadoPor = repositorioUsuario.buscarPorCorreoOUsername(username).getUsuario();

            Long idCreadoPor = creadoPor.getIdUsuario();
            Long idResponsable = actividad.getResponsable().getIdUsuario();

            if (!idCreadoPor.equals(idResponsable)) {

                String mensaje = Mensajes.getString("notificacion.actividad.asignar.nueva.actividad.mensaje",
                        actividad.getId(),
                        actividad.getNombre(),
                        actividad.getFechaCierreEsperado(),
                        actividad.getProyecto().getIdProyecto());

                Notificacion notificacion = servicioNotificacion.construirNotificacion(idResponsable,
                        actividad.getProyecto().getIdProyecto(),
                        mensaje,
                        TipoNotificacion.INFO);

                // notificar dentro de la aplicacion
                servicioNotificacion.crearNotificacion(notificacion);

                // enviar correo
                String para = dto.getResponsable().getCorreo();
                String subject = Mensajes.getString("notificacion.actividad.asignar.nueva.actividad", creadoPor.getNombres(), creadoPor.getApellidos());
                servicioCorreo.enviarCorreo(para, subject, mensaje);
            }

        } catch (Exception e) {
            logger.error("Fallo al notificar responsable actividad", e);
        }

    }


    @Async
    @Override
    public void notificarNuevoProyecto(Proyecto nuevoProyecto) {
        try {
            String mensaje = Mensajes.getString("notificacion.proyecto.crear.nuevo.proyecto",
                    nuevoProyecto.getIdProyecto(),
                    nuevoProyecto.getNombreProyecto());

            Notificacion notificacion = servicioNotificacion.construirNotificacion(null,
                    nuevoProyecto.getIdProyecto(),
                    mensaje,
                    TipoNotificacion.INFO);

            // notificar dentro de la aplicacion
            servicioNotificacion.crearNotificacion(notificacion);

        } catch (Exception e) {
            logger.error("Fallo al notificar nuevo proyecto", e);
        }
    }


    @Async
    @Override
    public void notificarNuevoMiembroProyecto(Long idProyecto, UsuarioAutenticado miembro) {
        try {
            Usuario usuario = miembro.getUsuario();
            String nombreCompleto = usuario.getNombres() + " " + usuario.getApellidos();

            String mensaje = Mensajes.getString("notificacion.proyecto.nuevo.miembro",
                    nombreCompleto,
                    idProyecto);

            Notificacion notificacionUsuario = servicioNotificacion.construirNotificacion(miembro.getUsuario().getIdUsuario(),
                    null,
                    mensaje,
                    TipoNotificacion.INFO);

            Notificacion notificacionProyecto = servicioNotificacion.construirNotificacion(null,
                    idProyecto,
                    mensaje,
                    TipoNotificacion.INFO);

            // notificar dentro de la aplicacion
            servicioNotificacion.crearNotificacion(notificacionUsuario);
            servicioNotificacion.crearNotificacion(notificacionProyecto);

            // enviar correo
            String para = usuario.getCorreo();
            String subject = Mensajes.getString("notificacion.proyecto.nuevo.miembro.subject");
            servicioCorreo.enviarCorreo(para, subject, mensaje);

        } catch (Exception e) {
            logger.error("Fallo al notificar agregar miembro proyecto", e);
        }
    }


    @Async
    @Override
    public void notificarEliminarMiembroProyecto(Long idProyecto, Long idMiembro) {
        try {
            String mensaje = Mensajes.getString("notificacion.proyecto.quitar.miembro", idProyecto);

            Notificacion notificacionUsuario = servicioNotificacion.construirNotificacion(idMiembro,
                    idProyecto,
                    mensaje,
                    TipoNotificacion.WARN);

            // notificar dentro de la aplicacion
            servicioNotificacion.crearNotificacion(notificacionUsuario);

        } catch (Exception e) {
            logger.error("Fallo al notificar quitar miembro proyecto", e);
        }
    }

}
