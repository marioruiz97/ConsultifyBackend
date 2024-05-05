package com.asisge.consultifybackend.proyectos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @deprecated usar la clase {@link com.asisge.consultifybackend.clientes.dominio.modelo.Cliente}
 */
@Deprecated(forRemoval = true, since = "V1")
@AllArgsConstructor
public @Data class ClienteProyecto {

    private Long idCliente;
    private String nombreCliente;
}
