package com.asisge.consultifybackend.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Usuario {

    private Long idUsuario;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;

}
