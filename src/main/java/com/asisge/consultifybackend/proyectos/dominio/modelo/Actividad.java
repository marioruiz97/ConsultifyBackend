package com.asisge.consultifybackend.proyectos.dominio.modelo;

import com.asisge.consultifybackend.usuarios.dominio.modelo.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class Actividad {

    private Long id;
    private String nombre;
    private String descripcion;
    private EstadoActividad estado;
    private LocalDate fechaCierreEsperado;
    private UsuarioAutenticado responsable;
    private List<Seguimiento> seguimiento;
}
