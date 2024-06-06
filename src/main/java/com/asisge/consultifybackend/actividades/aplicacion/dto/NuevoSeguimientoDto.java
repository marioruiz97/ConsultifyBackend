package com.asisge.consultifybackend.actividades.aplicacion.dto;

import com.asisge.consultifybackend.actividades.dominio.modelo.Actividad;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NuevoSeguimientoDto {

    @NotBlank
    private String comentarios;

    @Valid
    @NotNull
    private Actividad actividad;

    private String username;

}
