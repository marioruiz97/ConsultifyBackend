package com.asisge.consultifybackend.usuarios.dominio.modelo;


import com.asisge.consultifybackend.utilidad.dominio.modelo.ExpresionRegular;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString(exclude = {"contrasena", "ultimoInicio"})
@AllArgsConstructor
public class UsuarioAutenticado {

    private Usuario usuario;
    private String nombreUsuario;
    private String contrasena;
    private String creadoPor;
    private LocalDateTime ultimoInicio;
    @Setter
    private Boolean activo;
    @Setter
    private Boolean verificado;
    private Rol rol;

    public void cambiarContrasena(String nuevaContrasena) {
        if (!nuevaContrasena.matches(ExpresionRegular.PATRON_CONTRASENA)) {
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
                this.activo != null &&
                this.verificado != null &&
                this.rol != null;
        return valido;
    }

    private boolean validarContrasena() {
        return this.contrasena.matches(ExpresionRegular.PATRON_CONTRASENA);
    }

    public boolean validarEditarUsuarioAutenticado() {
        return this.nombreUsuario != null &&
                this.activo != null &&
                this.verificado != null &&
                this.rol != null;
    }

    public void guardarClaveEncriptada(String contrasena) {
        if (contrasena.length() > 20) this.contrasena = contrasena;
    }
}
