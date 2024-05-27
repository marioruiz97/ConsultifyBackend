package com.asisge.consultifybackend.usuarios.aplicacion.dto;

import com.asisge.consultifybackend.utilidad.dominio.modelo.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CambioEstadoDto implements Dto {

    private Long idUsuario;
    private Boolean activo;

    @Override
    public boolean validarDto() {
        return this.idUsuario != null &&
                this.activo != null;
    }
}
