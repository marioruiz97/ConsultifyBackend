package com.asisge.consultifybackend.proyectos.dominio.puerto;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;

import java.util.ArrayList;
import java.util.List;

public interface RepositorioActividad {
    default List<Actividad> obtenerActividadesPorProyecto(Proyecto proyecto) {
        return new ArrayList<>();
    }


}
