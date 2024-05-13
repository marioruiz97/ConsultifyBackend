package com.asisge.consultifybackend.proyectos.aplicacion.dto;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Usuario;
import com.asisge.consultifybackend.utilidad.dominio.modelo.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MiembroDto implements Dto {

    @NotBlank
    private String nombreUsuario;

    @NotNull
    private Usuario usuario;

    @Override
    public boolean validarDto() {
        return nombreUsuario != null && !nombreUsuario.trim().equals("") &&
                usuario != null && usuario.getIdUsuario() != null;
    }
}
