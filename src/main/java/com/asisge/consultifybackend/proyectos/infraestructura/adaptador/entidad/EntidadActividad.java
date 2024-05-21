package com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.entidad.ModeloAuditoria;
import com.asisge.consultifybackend.proyectos.dominio.modelo.EstadoActividad;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private String nombre;

    @NotBlank
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EntidadProyecto.class, optional = false)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private EntidadProyecto proyecto;

    @Enumerated(EnumType.STRING)
    private EstadoActividad estado;

    @NotNull
    private LocalDate fechaCierreEsperado;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EntidadUsuario.class, optional = false)
    @JoinColumn(name = "id_responsable", nullable = false)
    private EntidadUsuario responsable;

}
