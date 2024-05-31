package com.asisge.consultifybackend.proyectos.aplicacion.manejador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import com.asisge.consultifybackend.notificaciones.aplicacion.servicio.ServicioNotificacion;
import com.asisge.consultifybackend.notificaciones.dominio.modelo.Notificacion;
import com.asisge.consultifybackend.notificaciones.dominio.modelo.TipoNotificacion;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.NotificadorActividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.usuarios.dominio.puerto.RepositorioUsuario;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.Mensajes;
import com.asisge.consultifybackend.utilidad.aplicacion.servicio.ServicioCorreo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ManejadorNotificadorProyecto implements NotificadorActividad {

    private final Logger logger = LoggerFactory.getLogger(ManejadorNotificadorProyecto.class);

    private final RepositorioUsuario repositorioUsuario;
    private final ServicioAutenticacion servicioAutenticacion;
    private final ServicioCorreo servicioCorreo;
    private final ServicioNotificacion servicioNotificacion;

    @Autowired
    public ManejadorNotificadorProyecto(RepositorioUsuario repositorioUsuario, ServicioAutenticacion servicioAutenticacion, ServicioCorreo servicioCorreo, ServicioNotificacion servicioNotificacion) {
        this.repositorioUsuario = repositorioUsuario;
        this.servicioAutenticacion = servicioAutenticacion;
        this.servicioCorreo = servicioCorreo;
        this.servicioNotificacion = servicioNotificacion;
    }

    @Async
    @Override
    public void notificarResponsableActividad(Actividad actividad) {

        try {
            String username = servicioAutenticacion.obtenerNombreUsuarioEnSesion();
            Usuario creadoPor = repositorioUsuario.buscarPorCorreoOUsername(username).getUsuario();

            Long idCreadoPor = creadoPor.getIdUsuario();
            Long idResponsable = actividad.getResponsable().getIdUsuario();

            if (!idCreadoPor.equals(idResponsable)) {

                String mensaje = Mensajes.getString("notificacion.actividad.asignar.nueva.actividad.mensaje", actividad.getId(), actividad.getNombre(), actividad.getFechaCierreEsperado());

                Notificacion notificacion = servicioNotificacion.construirNotificacion(idResponsable,
                        actividad.getProyecto().getIdProyecto(),
                        mensaje,
                        TipoNotificacion.INFO);

                // notificar dentro de la aplicacion
                servicioNotificacion.crearNotificacion(notificacion);

                // enviar correo
                String para = actividad.getResponsable().getCorreo();
                String subject = Mensajes.getString("notificacion.actividad.asignar.nueva.actividad", creadoPor.getNombres(), creadoPor.getApellidos());
                servicioCorreo.enviarCorreo(para, subject, mensaje);
            }

        } catch (Exception e) {
            logger.error("Fallo al notificar responsable actividad", e);
        }

    }


}
