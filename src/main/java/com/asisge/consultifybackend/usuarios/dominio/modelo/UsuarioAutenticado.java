package com.asisge.consultifybackend.usuarios.dominio.modelo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UsuarioAutenticado {

    private Usuario usuario;
    private String nombreUsuario;
    private String contrasena;
    private LocalDateTime creadoEn;
    private String creadoPor;
    private LocalDateTime ultimoInicio;
    @Setter
    private Boolean activo;
    @Setter
    private Boolean verificado;
    private Rol rol;

    public void cambiarContrasena(String nuevaContrasena) {
        if (!nuevaContrasena.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$")) {
            throw new IllegalArgumentException("La contraseña no cumple con el formato requerido");
        }
        this.contrasena = nuevaContrasena;
    }

    public void limpiarContrasena() {
        this.contrasena = null;
    }

    public boolean validarCrearUsuarioAutenticado() {
        boolean valido = this.validarContrasena();
        if (valido) valido = this.nombreUsuario != null &&
                this.contrasena != null &&
                this.creadoEn != null &&
                //this.creadoPor != null &&
                this.ultimoInicio != null &&
                this.activo != null &&
                this.verificado != null &&
                this.rol != null;
        return valido;
    }

    private boolean validarContrasena() {
        return this.contrasena.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$");
    }

    public boolean validarEditarUsuarioAutenticado() {
        return this.nombreUsuario != null &&
                this.creadoEn != null &&
                this.creadoPor != null &&
                this.ultimoInicio != null &&
                this.activo != null &&
                this.verificado != null &&
                this.rol != null;
    }

    public void guardarClaveEncriptada(String contrasena) {
        if (contrasena.length() > 16) this.contrasena = contrasena;
    }
}
