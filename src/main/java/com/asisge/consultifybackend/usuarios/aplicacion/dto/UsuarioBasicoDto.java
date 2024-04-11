package com.asisge.consultifybackend.usuarios.aplicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UsuarioBasicoDto implements Dto {

    private Long idUsuario;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private String telefono;

    @Override
    public boolean validarDto() {
        return this.identificacion != null &&
                this.nombres != null &&
                this.apellidos != null &&
                this.telefono != null;
    }
}
