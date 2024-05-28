package com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Table(name = "token_verificacion")
@NoArgsConstructor
@EqualsAndHashCode
public @Data class EntidadTokenVerificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idToken;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(targetEntity = EntidadUsuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id")
    private EntidadUsuario usuario;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiraEn;

    public EntidadTokenVerificacion(EntidadUsuario usuario) {
        this.usuario = usuario;
        this.token = UUID.randomUUID().toString();
    }

    @PrePersist
    public void setFechas() {
        this.creadoEn = LocalDateTime.now(ZoneId.systemDefault());
        this.expiraEn = creadoEn.plusHours(1);
    }

}
