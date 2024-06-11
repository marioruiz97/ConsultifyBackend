package com.asisge.consultifybackend.informes.infraestructura.controlador;

import com.asisge.consultifybackend.autenticacion.aplicacion.servicio.ServicioAutenticacion;
import com.asisge.consultifybackend.informes.aplicacion.servicio.ServicioExportarInforme;
import com.asisge.consultifybackend.informes.aplicacion.servicio.ServicioInforme;
import com.asisge.consultifybackend.informes.dominio.modelo.InformeProyecto;
import com.asisge.consultifybackend.proyectos.aplicacion.servicio.ServicioSeguridadProyecto;
import com.asisge.consultifybackend.utilidad.dominio.excepcion.AccionNoPermitidaException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/informes")
@PreAuthorize("isAuthenticated()")
public class ControladorInformes {

    private final Logger logger = LoggerFactory.getLogger(ControladorInformes.class);

    private final ServicioSeguridadProyecto servicioSeguridad;
    private final ServicioAutenticacion servicioAutenticacion;
    private final ServicioInforme servicioInforme;
    private final ServicioExportarInforme servicioExportacion;

    @Autowired
    public ControladorInformes(ServicioSeguridadProyecto servicioSeguridad,
                               ServicioAutenticacion servicioAutenticacion,
                               ServicioInforme servicioInforme,
                               ServicioExportarInforme servicioExportacion) {
        this.servicioSeguridad = servicioSeguridad;
        this.servicioAutenticacion = servicioAutenticacion;
        this.servicioInforme = servicioInforme;
        this.servicioExportacion = servicioExportacion;
    }


    @Cacheable("informeProyecto")
    @GetMapping
    public List<InformeProyecto> obtenerEncabezadosInformes() {

        if (servicioSeguridad.esAdmin()) {
            logger.info("Obtener todos los encabezados - Admin");
            return servicioInforme.adminObtenerTodosEncabezados();

        } else {
            @Valid String usernameOCorreo = servicioAutenticacion.obtenerNombreUsuarioEnSesion();
            logger.info("Obtener mis encabezados - {}", usernameOCorreo);
            return servicioInforme.obtenerEncabezadoMisProyectos(usernameOCorreo);
        }
    }


    @Cacheable(value = "informeActividades", key = "#idProyecto")
    @GetMapping("/{idProyecto}")
    public InformeProyecto obtenerInformeProyecto(@PathVariable Long idProyecto) {
        @Valid String username = servicioAutenticacion.obtenerNombreUsuarioEnSesion();

        if (servicioSeguridad.esAdmin() || servicioSeguridad.esMiembroProyecto(idProyecto, username)) {
            logger.info("Obtener informe - {}", idProyecto);
            return servicioInforme.obtenerInformeProyecto(idProyecto);

        } else throw new AccionNoPermitidaException("No tienes permiso de obtener esta información");
    }

    @Cacheable(value = "archivoInforme", key = "#idProyecto", condition = "#format.equalsIgnoreCase('pdf')", unless = "#format.contains('xls')")
    @GetMapping("/exportar/{idProyecto}")
    public ResponseEntity<byte[]> getReport(@RequestParam String format, @PathVariable Long idProyecto) {
        @Valid String username = servicioAutenticacion.obtenerNombreUsuarioEnSesion();
        if (servicioSeguridad.esAdmin() || servicioSeguridad.esMiembroProyecto(idProyecto, username)) {
            try {
                byte[] data = servicioExportacion.exportarReporte(idProyecto, format);

                LocalDate ahora = LocalDate.now();
                String nombre = idProyecto + "-" + ahora;

                HttpHeaders headers = new HttpHeaders();
                headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=informeActividadReporte-" + nombre + "." + format);

                if (format.equalsIgnoreCase("pdf")) {
                    headers.setContentType(MediaType.APPLICATION_PDF);
                } else if (format.toLowerCase().contains("xls")) {
                    headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                }

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(data);

            } catch (Exception e) {
                logger.error("Error exportando informe ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        } else throw new AccionNoPermitidaException("No tienes permiso de obtener esta información");

    }


}
