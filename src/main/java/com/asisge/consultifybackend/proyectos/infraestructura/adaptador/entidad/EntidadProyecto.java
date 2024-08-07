package com.asisge.consultifybackend.proyectos.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.entidad.ModeloAuditoria;
import com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad.EntidadCliente;
import com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad.EntidadUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "proyecto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class EntidadProyecto extends ModeloAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProyecto;

    @NotBlank
    private String nombreProyecto;

    @NotBlank
    private String descripcionProyecto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente")
    private EntidadCliente clienteProyecto;

    private LocalDate cierreEsperado;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = EntidadUsuario.class, cascade = CascadeType.REFRESH)
    @JoinTable(name = "miembro_proyecto",
            joinColumns = @JoinColumn(name = "id_proyecto"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_proyecto", "id_usuario"})
    )
    private List<EntidadUsuario> miembros;

    public EntidadProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public void agregarMiembro(EntidadUsuario nuevoMiembro) {
        if (this.miembros == null) this.miembros = List.of(nuevoMiembro);
        this.miembros.add(nuevoMiembro);
    }

    public void quitarMiembro(Long idMiembro) {
        if (this.miembros != null) {
            this.miembros.removeIf(quitar -> quitar.getIdUsuario().equals(idMiembro));
        }
    }
}
