package com.asisge.consultifybackend.informes.aplicacion.manejador;

import com.asisge.consultifybackend.informes.aplicacion.servicio.ServicioExportarInforme;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
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

    private static byte[] exportarExcel(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setOnePagePerSheet(false);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        return outputStream.toByteArray();
    }

    private static byte[] exportarPDF(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream.toByteArray();
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
                data = exportarPDF(jasperPrint);

            } else if (reportFormat.toLowerCase().contains("xls")) {
                data = exportarExcel(jasperPrint);
            }


        } catch (Exception e) {
            logger.error("Error exportando reporte de actividades", e);
        }
        return data;
    }

}
