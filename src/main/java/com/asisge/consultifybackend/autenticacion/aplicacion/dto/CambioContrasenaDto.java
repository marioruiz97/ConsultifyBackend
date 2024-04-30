package com.asisge.consultifybackend.autenticacion.aplicacion.dto;

import com.asisge.consultifybackend.usuarios.aplicacion.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CambioContrasenaDto implements Dto {

    private Long idUsuario;
    private String correo;
    private String contrasena;
    private String contrasenaActual;

    @Override
    public boolean validarDto() {
        return this.correo != null &&
                this.contrasena != null &&
                this.contrasenaActual != null &&
                !this.contrasena.equals(contrasenaActual);
    }
}
