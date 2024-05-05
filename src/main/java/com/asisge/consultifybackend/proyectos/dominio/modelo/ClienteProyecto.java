package com.asisge.consultifybackend.proyectos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class ClienteProyecto {

    private Long idCliente;
    private String nombreCliente;
}
