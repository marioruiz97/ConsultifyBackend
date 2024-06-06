package com.asisge.consultifybackend.actividades.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "seguimiento")
@AllArgsConstructor
@NoArgsConstructor
public @Data class EntidadSeguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguimiento;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EntidadActividad.class, optional = false)
    @JoinColumn(name = "id_actividad")
    private EntidadActividad actividad;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EntidadUsuario.class, optional = false)
    @JoinColumn(name = "id_usuario")
    private EntidadUsuario usuario;

    private LocalDateTime fechaSeguimiento;

    @Lob
    private String comentarios;
}
