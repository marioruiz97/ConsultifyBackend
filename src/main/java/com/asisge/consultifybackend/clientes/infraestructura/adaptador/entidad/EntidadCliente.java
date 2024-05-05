package com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.utilidad.dominio.modelo.TipoDocumento;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
    @Valid
    @UniqueElements
    @Getter(value = AccessLevel.NONE)
    private List<EntidadContactoCliente> contactos;

    public EntidadCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public List<EntidadContactoCliente> getContactos() {
        if (contactos != null)
            return contactos;
        else return new ArrayList<>();
    }

    /**
     * Método encargado de actualizar la lista de contactos de un cliente. Asigna la lista actualizada al cliente actual.
     * <p>Para su funcionamiento, se validan los siguientes casos:</p>
     * <p>1. si la lista actual es null, se asigna la nuevaLista de forma automática</p>
     * <p>2. si la lista actual no es null, pero está vacía, se agregan todos los elementos de la nuevaLista a la lista actual</p>
     * <p>3. si la lista actual no es null y no está vacía, en la lista actual solo se dejan los elementos en común, después en la lista nueva solo se dejan los elementos nuevos
     * (que no son comunes).</br> por último, se agregan los contactos nuevos a la lista actual</p>
     *
     * @param nuevaLista lista de contactos a guardar
     */
    public void actualizarContactos(List<EntidadContactoCliente> nuevaLista) {
        if (this.contactos != null) {
            if (!this.contactos.isEmpty()) {
                this.contactos.retainAll(nuevaLista);
                nuevaLista = new ArrayList<>(nuevaLista); // Crear una nueva lista mutable
                nuevaLista.removeAll(this.contactos);
            }

            this.contactos.addAll(nuevaLista);

        } else {
            this.contactos = nuevaLista;
        }
    }

    public void eliminarContactos() {
        if (this.contactos != null) this.contactos.clear();
    }
}
