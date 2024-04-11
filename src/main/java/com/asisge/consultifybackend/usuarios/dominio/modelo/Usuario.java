package com.asisge.consultifybackend.usuarios.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Usuario {

    private Long idUsuario;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;

    public Usuario(String identificacion, String nombres, String apellidos, String telefono, String correo) {
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
    }

    public boolean validarUsuario() {
        boolean valido = this.validarContacto();
        if (valido) valido = this.identificacion != null &&
                this.nombres != null &&
                this.apellidos != null &&
                this.telefono != null &&
                this.correo != null;
        return valido;
    }

    private boolean validarContacto() {
        return this.telefono.matches("^(60\\d)\\d{7}$|^(3\\d{9})$") &&
                this.correo.matches("^[a-zA-Z\\d._%+-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$");
    }

    public void cambiarCorreo(String correo) {
        this.correo = correo;
    }
}
