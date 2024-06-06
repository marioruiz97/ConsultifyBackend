package com.asisge.consultifybackend.actividades.dominio.modelo;

import lombok.Getter;

@Getter
public enum EstadoActividad {
    POR_HACER("Por hacer"),
    EN_PROGRESO("En progreso"),
    EN_REVISION("En revisión"),
    COMPLETADA("Completada");

    private final String estado;

    EstadoActividad(String estado) {
        this.estado = estado;
    }
}

