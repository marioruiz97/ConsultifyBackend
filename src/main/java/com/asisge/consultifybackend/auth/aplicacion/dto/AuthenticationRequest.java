package com.asisge.consultifybackend.auth.aplicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {

    private String nombreUsuario;
    private String contrasena;

}
