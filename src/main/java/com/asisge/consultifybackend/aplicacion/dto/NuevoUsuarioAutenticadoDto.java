package com.asisge.consultifybackend.aplicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NuevoUsuarioAutenticadoDto implements Dto {


    private String identificacion;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String nombreUsuario;
    private String creadoPor;

    @Override
    public boolean validarDto() {
        return this.identificacion != null &&
                this.nombres != null &&
                this.apellidos != null &&
                this.telefono != null &&
                this.correo != null &&
                this.nombreUsuario != null &&
                this.creadoPor != null;
    }

}
