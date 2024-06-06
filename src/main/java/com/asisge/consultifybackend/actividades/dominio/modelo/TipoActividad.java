package com.asisge.consultifybackend.actividades.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TipoActividad {

    @Setter
    private Long idTipo;
    private String nombre;

}
