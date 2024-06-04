package com.asisge.consultifybackend.actividades.infraestructura.adaptador.entidad;


import com.asisge.consultifybackend.actividades.dominio.modelo.TipoActividad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_actividad")
@AllArgsConstructor
@NoArgsConstructor
public @Data class EntidadTipoActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipo;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nombre;

    // convertidor
    public static TipoActividad aDominio(EntidadTipoActividad entidad) {
        return new TipoActividad(entidad.getIdTipo(), entidad.getNombre());
    }

}
