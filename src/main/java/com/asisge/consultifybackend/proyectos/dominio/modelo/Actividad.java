package com.asisge.consultifybackend.proyectos.dominio.modelo;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString(exclude = {"seguimiento", "proyecto"})
public class Actividad {

    private Long id;
    private String nombre;
    private String descripcion;
    private Proyecto proyecto;
    private EstadoActividad estado;
    private LocalDate fechaCierreEsperado;
    private Usuario responsable;
    private List<Seguimiento> seguimiento;
}
