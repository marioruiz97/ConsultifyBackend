package com.asisge.consultifybackend.autenticacion.aplicacion.dto;

import com.asisge.consultifybackend.usuarios.aplicacion.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ActualizarMisDatosDto implements Dto {

    private String identificacion;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String nombreUsuario;

    @Override
    public boolean validarDto() {
        return this.identificacion != null &&
                this.nombres != null &&
                this.apellidos != null &&
                this.telefono != null &&
                this.correo != null &&
                this.nombreUsuario != null;
    }
}
