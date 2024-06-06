package com.asisge.consultifybackend.informes.aplicacion.servicio;

import net.sf.jasperreports.engine.JRException;

public interface ServicioExportarInforme {

    byte[] exportarReporte(Long idProyecto, String reportFormat) throws JRException;

}
