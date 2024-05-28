package com.asisge.consultifybackend.autenticacion.dominio.modelo;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TokenVerificacion {

    private Long idToken;

    @ToString.Exclude
    private String token;

    private Usuario usuario;

    private LocalDateTime creadoEn;

    private LocalDateTime expiraEn;

    public boolean haExpirado() {
        return LocalDateTime.now().isAfter(this.expiraEn);
    }
}
