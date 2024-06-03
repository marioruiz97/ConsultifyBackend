package com.asisge.consultifybackend.notificaciones.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.notificaciones.dominio.modelo.TipoNotificacion;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "notificaciones")
@AllArgsConstructor
@NoArgsConstructor
public @Data class EntidadNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipoNotificacion;

    private String mensaje;

    private Long idUsuario;

    private Long idProyecto;

    @CreatedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime creadoEn;

    private boolean leida;

    @PrePersist
    public void setCreadoEn() {
        this.creadoEn = LocalDateTime.now(ZoneId.systemDefault());
    }

}
