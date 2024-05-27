package com.asisge.consultifybackend.informes.infraestructura.controlador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import com.asisge.consultifybackend.informes.aplicacion.servicio.ServicioInforme;
import com.asisge.consultifybackend.informes.dominio.modelo.InformeProyecto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguridadProyecto;
import com.asisge.consultifybackend.utilidad.dominio.excepcion.AccionNoPermitidaException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/informes")
@PreAuthorize("isAuthenticated()")
public class ControladorInformes {

    private final ServicioSeguridadProyecto servicioSeguridad;
    private final ServicioAutenticacion servicioAutenticacion;
    private final ServicioInforme servicioInforme;

    @Autowired
    public ControladorInformes(ServicioSeguridadProyecto servicioSeguridad, ServicioAutenticacion servicioAutenticacion, ServicioInforme servicioInforme) {
        this.servicioSeguridad = servicioSeguridad;
        this.servicioAutenticacion = servicioAutenticacion;
        this.servicioInforme = servicioInforme;
    }


    @Cacheable("informeProyecto")
    @GetMapping
    public List<InformeProyecto> obtenerEncabezadosInformes() {

        if (servicioSeguridad.esAdmin()) {
            return servicioInforme.adminObtenerTodosEncabezados();

        } else {
            @Valid String usernameOCorreo = servicioAutenticacion.obtenerNombreUsuarioEnSesion();
            return servicioInforme.obtenerEncabezadoMisProyectos(usernameOCorreo);
        }
    }


    @Cacheable(value = "informeActividades", key = "#idProyecto")
    @GetMapping("/{idProyecto}")
    public InformeProyecto obtenerInformeProyecto(@PathVariable Long idProyecto) {
        @Valid String username = servicioAutenticacion.obtenerNombreUsuarioEnSesion();

        if (servicioSeguridad.esAdmin() || servicioSeguridad.esMiembroProyecto(idProyecto, username)) {
            return servicioInforme.obtenerInformeProyecto(idProyecto);

        } else throw new AccionNoPermitidaException("No tienes permiso de obtener esta información");
    }


}
