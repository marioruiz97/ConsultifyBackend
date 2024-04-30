package com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.utilidad.dominio.modelo.TipoDocumento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente", uniqueConstraints = @UniqueConstraint(columnNames = "numeroIdentificacion"))
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntidadCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @NotBlank
    @Size(max = 20)
    private String numeroIdentificacion;

    @NotBlank
    private String nombreComercial;

    @NotBlank
    private String razonSocial;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_asociado")
    @UniqueElements
    @Getter(value = AccessLevel.NONE)
    private List<EntidadContactoCliente> contactos;

    public List<EntidadContactoCliente> getContactos() {
        if (contactos != null && !contactos.isEmpty())
            return contactos;
        else return new ArrayList<>();
    }
}
