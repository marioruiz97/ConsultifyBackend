package com.asisge.consultifybackend.actividades.dominio.modelo;

import com.asisge.consultifybackend.proyectos.dominio.modelo.Proyecto;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString(exclude = "proyecto")
public class Actividad {

    private Long id;
    private String nombre;
    private String descripcion;
    private Proyecto proyecto;
    private @Setter EstadoActividad estado;
    private LocalDate fechaCierreEsperado;
    private Usuario responsable;

    private LocalDateTime fechaCompletada;

    public Actividad(Long id) {
        this.id = id;
    }
}
