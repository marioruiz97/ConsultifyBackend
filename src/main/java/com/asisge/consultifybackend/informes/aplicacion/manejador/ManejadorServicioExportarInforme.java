package com.asisge.consultifybackend.informes.aplicacion.manejador;

import com.asisge.consultifybackend.informes.aplicacion.servicio.ServicioExportarInforme;
import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ManejadorServicioExportarInforme implements ServicioExportarInforme {

    private final Logger logger = LoggerFactory.getLogger(ManejadorServicioExportarInforme.class);

    private final DataSource dataSource;

    @Autowired
    public ManejadorServicioExportarInforme(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public byte[] exportarReporte(Long idProyecto, String reportFormat) throws JRException {
        // Cargar el archivo .jrxml
        InputStream reportStream = getClass().getResourceAsStream("/reports/informe-actividades.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Rellenar el reporte con datos
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("IMAGE_DIR", "reports/images/");
        parameters.put("ID_PROYECTO", idProyecto);


        byte[] data = null;
        try (Connection connection = dataSource.getConnection()) {

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Exportar el reporte al formato deseado
            if (reportFormat.equalsIgnoreCase("pdf")) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                data = outputStream.toByteArray();
            }
            // Otros formatos pueden ser agregados aquí (HTML, XLS, etc.)

        } catch (Exception e) {
            logger.error("Error exportando reporte de actividades", e);
        }
        return data;
    }

}
