package com.asisge.consultifybackend.usuarios.aplicacion.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CambioCorreoDto implements Dto {

    private Long idUsuario;
    private String identificacion;
    private String correo;

    @Override
    public boolean validarDto() {
        return this.identificacion != null &&
                this.correo != null;
    }
}
