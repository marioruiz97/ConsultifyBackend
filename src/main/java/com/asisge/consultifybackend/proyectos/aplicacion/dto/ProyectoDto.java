package com.asisge.consultifybackend.proyectos.aplicacion.dto;


import com.asisge.consultifybackend.utilidad.dominio.modelo.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProyectoDto implements Dto {

    private Long idProyecto;

    @NotBlank
    private String nombreProyecto;

    @NotNull
    private Long idClienteProyecto;

    @NotBlank
    private String descripcionProyecto;

    @NotBlank
    private String creadoPor;

    @Override
    public boolean validarDto() {
        return nombreProyecto != null &&
                idClienteProyecto != null && idClienteProyecto != 0 &&
                descripcionProyecto != null &&
                creadoPor != null;
    }
}
