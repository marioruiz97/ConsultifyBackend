package com.asisge.consultifybackend.actividades.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.actividades.dominio.modelo.EstadoActividad;
import com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.entidad.ModeloAuditoria;
import com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad.EntidadProyecto;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "actividad")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class EntidadActividad extends ModeloAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotBlank
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EntidadProyecto.class, optional = false)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private EntidadProyecto proyecto;

    @Enumerated(EnumType.STRING)
    private EstadoActividad estado;

    private LocalDate fechaCierreEsperado;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = EntidadTipoActividad.class)
    @JoinColumn(name = "id_tipo_actividad")
    private EntidadTipoActividad tipoActividad;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EntidadUsuario.class, optional = false)
    @JoinColumn(name = "id_responsable", nullable = false)
    private EntidadUsuario responsable;

    private LocalDateTime fechaCompletada;

    public EntidadActividad(Long id) {
        this.id = id;
    }

    @PreUpdate
    public void setCompletada() {
        if (estado.equals(EstadoActividad.COMPLETADA))
            fechaCompletada = LocalDateTime.now(ZoneId.systemDefault());
        else fechaCompletada = null;
    }
}
