package com.asisge.consultifybackend.informes.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class InformeActividad {

    private int totalActividades;
    private int actividadesCompletas;
    private int actividadesPorVencer;

    private int actividadesPorHacer;
    private int actividadesEnProgreso;
    private int actividadesEnRevision;
}
