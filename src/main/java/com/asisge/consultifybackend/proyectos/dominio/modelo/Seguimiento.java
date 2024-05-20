package com.asisge.consultifybackend.proyectos.dominio.modelo;

import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Seguimiento {

    private Long idSeguimiento;
    private Actividad actividad;
    private UsuarioAutenticado usuario;
    private LocalDateTime fechaSeguimiento;
    private String comentarios;

}
