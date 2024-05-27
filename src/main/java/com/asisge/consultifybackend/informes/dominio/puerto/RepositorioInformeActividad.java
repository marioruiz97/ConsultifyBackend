package com.asisge.consultifybackend.informes.dominio.puerto;

public interface RepositorioInformeActividad {

    int obtenerTotalActividades(Long idProyecto);

    int obtenerActividadesCompletas(Long idProyecto);

    int obtenerActividadesPorVencer(Long idProyecto);

    int obtenerActividadesPorHacer(Long idProyecto);

    int obtenerActividadesEnProgreso(Long idProyecto);

    int obtenerActividadesEnRevision(Long idProyecto);

}
