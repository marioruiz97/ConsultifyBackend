package com.asisge.consultifybackend.proyectos.dominio.modelo;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class Seguimiento {

    private Long idSeguimiento;
    private Actividad actividad;
    private Usuario usuario;
    private LocalDateTime fechaSeguimiento;
    private String comentarios;

}
