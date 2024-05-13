package com.asisge.consultifybackend.proyectos.aplicacion.dto;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Actividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TableroProyecto {

    private Proyecto infoProyecto;
    private List<Actividad> actividades;

}
