package com.asisge.consultifybackend.autenticacion.aplicacion.dto;

import com.asisge.consultifybackend.utilidad.dominio.modelo.ExpresionRegular;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {

    @NotBlank
    private String nombreUsuario;
    @NotBlank
    @Pattern(regexp = ExpresionRegular.PATRON_CONTRASENA)
    private String contrasena;

}
