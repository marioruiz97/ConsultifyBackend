package com.asisge.consultifybackend.informes.aplicacion.servicio;

import com.asisge.consultifybackend.informes.dominio.modelo.InformeProyecto;

import java.util.List;

public interface ServicioInforme {

    List<InformeProyecto> adminObtenerTodosEncabezados();

    List<InformeProyecto> obtenerEncabezadoMisProyectos(String usernameOCorreo);

    InformeProyecto obtenerInformeProyecto(Long idProyecto);
}
