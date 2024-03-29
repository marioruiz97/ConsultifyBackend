package com.asisge.consultifybackend.aplicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CambioContrasenaDto implements Dto {

    private Long idUsuario;
    private String identificacion;
    private String correo;
    private String contrasena;
    private String contrasenaActual;

    @Override
    public boolean validarDto() {
        return this.identificacion != null &&
                this.correo != null &&
                this.contrasena != null &&
                this.contrasenaActual != null &&
                !this.contrasena.equals(contrasenaActual);
    }
}
