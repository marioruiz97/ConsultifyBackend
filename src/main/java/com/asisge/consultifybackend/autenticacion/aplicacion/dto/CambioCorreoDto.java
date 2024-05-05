package com.asisge.consultifybackend.autenticacion.aplicacion.dto;


import com.asisge.consultifybackend.utilidad.dominio.modelo.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CambioCorreoDto implements Dto {

    private Long idUsuario;
    private String correoActual;
    private String correoNuevo;

    @Override
    public boolean validarDto() {
        return this.idUsuario != null &&
                this.correoActual != null &&
                this.correoNuevo != null &&
                !this.correoActual.equals(this.correoNuevo);
    }
}
