package com.asisge.consultifybackend.aplicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CambioEstadoDto implements Dto {

    private Long idUsuario;
    private String identificacion;
    private Boolean activo;

    @Override
    public boolean validarDto() {
        return this.identificacion != null &&
                this.activo != null;
    }
}
