package com.asisge.consultifybackend.usuarios.dominio.modelo;

import com.asisge.consultifybackend.utilidad.dominio.modelo.ExpresionRegular;
import com.asisge.consultifybackend.utilidad.dominio.modelo.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Usuario {

    private Long idUsuario;
    private String identificacion;
    private TipoDocumento tipoDocumento;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;

    public Usuario(String identificacion, TipoDocumento tipoDocumento, String nombres, String apellidos, String telefono, String correo) {
        this.identificacion = identificacion;
        this.tipoDocumento = tipoDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
    }

    public boolean validarUsuario() {
        boolean valido = this.validarContacto();
        if (valido) valido = this.identificacion != null &&
                this.tipoDocumento != null &&
                this.nombres != null &&
                this.apellidos != null &&
                this.telefono != null &&
                this.correo != null;
        return valido;
    }

    private boolean validarContacto() {
        return this.telefono.matches(ExpresionRegular.PATRON_TELEFONO) &&
                this.correo.matches(ExpresionRegular.PATRON_CORREO);
    }

    public void cambiarCorreo(String correo) {
        this.correo = correo;
    }
}
