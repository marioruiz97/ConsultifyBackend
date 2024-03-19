package com.asisge.consultifybackend.aplicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class NuevoUsuarioAutenticadoDto {

    private Long idUsuario;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String nombreUsuario;
    private String contrasena;
    private LocalDateTime creadoEn;
    private String creadoPor;
    private LocalDateTime ultimoInicio;
    private Boolean activo;
    private Boolean verificado;
}
