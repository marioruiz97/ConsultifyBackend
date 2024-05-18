package com.asisge.consultifybackend.proyectos.aplicacion.dto;

import com.asisge.consultifybackend.proyectos.dominio.modelo.EstadoActividad;
import com.asisge.consultifybackend.proyectos.dominio.modelo.Seguimiento;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

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
    @FutureOrPresent
    private LocalDate fechaCierreEsperado;

    private Usuario responsable;

    private List<Seguimiento> seguimiento;

}
