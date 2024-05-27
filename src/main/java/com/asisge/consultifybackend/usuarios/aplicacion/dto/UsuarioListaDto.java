package com.asisge.consultifybackend.usuarios.aplicacion.dto;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Rol;
import com.asisge.consultifybackend.utilidad.dominio.modelo.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UsuarioListaDto {

    private Long idUsuario;
    private String identificacion;
    private TipoDocumento tipoDocumento;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String nombreUsuario;
    private String correo;
    private Boolean estado;
    private Rol rol;
    private String creadoPor;
    private LocalDateTime ultimoInicio;
}
