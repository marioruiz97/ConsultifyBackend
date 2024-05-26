package com.asisge.consultifybackend.informes.dominio.puerto;

import com.asisge.consultifybackend.informes.dominio.modelo.InformeProyecto;

import java.util.List;

public interface RepositorioInformeProyecto {

    List<InformeProyecto> buscarEncabezados();

    List<InformeProyecto> buscarMisEncabezados(String usernameOCorreo);

    InformeProyecto obtenerInformeProyecto(Long idProyecto);
}
