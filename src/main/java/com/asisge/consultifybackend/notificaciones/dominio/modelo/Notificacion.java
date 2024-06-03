package com.asisge.consultifybackend.notificaciones.dominio.modelo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder(setterPrefix = "con")
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
