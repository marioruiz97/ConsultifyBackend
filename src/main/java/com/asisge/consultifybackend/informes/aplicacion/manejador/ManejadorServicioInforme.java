package com.asisge.consultifybackend.informes.aplicacion.manejador;

import com.asisge.consultifybackend.informes.aplicacion.servicio.ServicioInforme;
import com.asisge.consultifybackend.informes.dominio.modelo.InformeActividad;
import com.asisge.consultifybackend.informes.dominio.modelo.InformeProyecto;
import com.asisge.consultifybackend.informes.dominio.puerto.RepositorioInformeActividad;
import com.asisge.consultifybackend.informes.dominio.puerto.RepositorioInformeProyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManejadorServicioInforme implements ServicioInforme {

    private final RepositorioInformeProyecto repositorioInformeProyecto;
    private final RepositorioInformeActividad repositorioInformeActividad;

    @Autowired
    public ManejadorServicioInforme(RepositorioInformeProyecto repositorioInformeProyecto, RepositorioInformeActividad repositorioInformeActividad) {
        this.repositorioInformeProyecto = repositorioInformeProyecto;
        this.repositorioInformeActividad = repositorioInformeActividad;
    }

    private static InformeActividad construirInformeActividad(int totalActividades, int actividadesCompletas, int actividadesPorVencer,
                                                              int actividadesPorHacer, int actividadesEnProgreso, int actividadesEnRevision) {
        return new InformeActividad(
                totalActividades,
                actividadesCompletas,
                actividadesPorVencer,
                actividadesPorHacer,
                actividadesEnProgreso,
                actividadesEnRevision);
    }

    @Secured("ROLE_ADMIN")
    @Override
    public List<InformeProyecto> adminObtenerTodosEncabezados() {
        return repositorioInformeProyecto.buscarEncabezados();
    }

    @Override
    public List<InformeProyecto> obtenerEncabezadoMisProyectos(String usernameOCorreo) {
        return repositorioInformeProyecto.buscarMisEncabezados(usernameOCorreo);
    }

    @Override
    public InformeProyecto obtenerInformeProyecto(Long idProyecto) {
        InformeProyecto informeProyecto = repositorioInformeProyecto.obtenerInformeProyecto(idProyecto);

        int totalActividades = repositorioInformeActividad.obtenerTotalActividades(idProyecto);
        int actividadesCompletas = repositorioInformeActividad.obtenerActividadesCompletas(idProyecto);
        int actividadesPorVencer = repositorioInformeActividad.obtenerActividadesPorVencer(idProyecto);

        int actividadesPorHacer = repositorioInformeActividad.obtenerActividadesPorHacer(idProyecto);
        int actividadesEnProgreso = repositorioInformeActividad.obtenerActividadesEnProgreso(idProyecto);
        int actividadesEnRevision = repositorioInformeActividad.obtenerActividadesEnRevision(idProyecto);

        InformeActividad informeActividad = construirInformeActividad(totalActividades, actividadesCompletas, actividadesPorVencer, actividadesPorHacer, actividadesEnProgreso, actividadesEnRevision);
        informeProyecto.setInformeActividad(informeActividad);

        return informeProyecto;
    }
}
