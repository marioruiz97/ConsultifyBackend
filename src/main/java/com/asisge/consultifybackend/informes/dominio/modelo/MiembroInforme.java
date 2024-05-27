package com.asisge.consultifybackend.informes.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MiembroInforme {

    private Long idUsuario;
    private String nombreCompleto;

}