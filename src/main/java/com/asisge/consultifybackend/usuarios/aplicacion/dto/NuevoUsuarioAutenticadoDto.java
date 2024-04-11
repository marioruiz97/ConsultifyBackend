package com.asisge.consultifybackend.usuarios.aplicacion.dto;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Rol;
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
    private Rol rol;

    @Override
    public boolean validarDto() {
        return this.identificacion != null &&
                this.nombres != null &&
                this.apellidos != null &&
                this.telefono != null &&
                this.correo != null &&
                this.nombreUsuario != null &&
                this.creadoPor != null &&
                this.rol != null;
    }

}
