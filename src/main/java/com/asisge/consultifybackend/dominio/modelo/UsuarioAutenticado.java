package com.asisge.consultifybackend.dominio.modelo;


import lombok.AllArgsConstructor;
import lombok.Getter;

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
    private Boolean activo;
    private Boolean verificado;

    public void cambiarContrasena(String nuevaContrasena) {
        if (!nuevaContrasena.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$")) {
            throw new IllegalArgumentException("La contraseña no cumple con el formato requerido");
        }
        this.contrasena = nuevaContrasena;
    }

    public void limpiarContrasena() {
        this.contrasena = null;
    }
}
