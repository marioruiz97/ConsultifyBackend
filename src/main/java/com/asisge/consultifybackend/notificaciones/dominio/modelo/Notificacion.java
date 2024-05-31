package com.asisge.consultifybackend.notificaciones.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    private Long id;

    private TipoNotificacion tipoNotificacion;

    private String mensaje;

    private Long idUsuario;

    private Long idProyecto;

    private LocalDateTime creadoEn;

    private boolean leida;

}
