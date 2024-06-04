package com.asisge.consultifybackend.actividades.aplicacion.dto;

import com.asisge.consultifybackend.actividades.dominio.modelo.EstadoActividad;
import com.asisge.consultifybackend.actividades.dominio.modelo.TipoActividad;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ActividadDto {

    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    @NotNull
    private EstadoActividad estado;

    @Nullable
    @FutureOrPresent(message = "La fecha de cierre esperada debe ser minimo hoy o una fecha futura")
    private LocalDate fechaCierreEsperado;

    @Nullable
    private TipoActividad tipoActividad;

    private Usuario responsable;

    @Nullable
    private LocalDateTime fechaCompletada;

}
