package com.asisge.consultifybackend.informes.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class InformeProyecto {

    private Long idProyecto;
    private String nombreProyecto;
    private ClienteInforme cliente;
    private Boolean abierto;
    private List<MiembroInforme> miembros;
    private InformeActividad informeActividad;

}
