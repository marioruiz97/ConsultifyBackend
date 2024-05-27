package com.asisge.consultifybackend.proyectos.aplicacion.dto;

import com.asisge.consultifybackend.proyectos.dominio.modelo.EstadoActividad;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CambioEstadoActividadDto {

    @NotNull
    private Long id;

    @NotNull
    private EstadoActividad estado;
}
