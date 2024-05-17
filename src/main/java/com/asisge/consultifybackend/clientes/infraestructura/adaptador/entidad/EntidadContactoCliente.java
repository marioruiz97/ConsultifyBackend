package com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.utilidad.dominio.modelo.ExpresionRegular;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;


@Entity
@Table(name = "contacto_cliente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntidadContactoCliente {

    @Id
    @EqualsAndHashCode.Exclude
    @UuidGenerator
    @Column(name = "id_contacto")
    private String id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nombreCompleto;

    @NotBlank
    @Column
    private String cargo;

    @Pattern(regexp = ExpresionRegular.PATRON_TELEFONO)
    @NotBlank
    @NotNull
    private String telefono;

    @NotBlank
    @NotNull
    @Email(regexp = "^[\\w\\.]+@([\\w-]+)\\.+[\\w-]{2,}$", flags = {Pattern.Flag.CASE_INSENSITIVE})
    @Column(nullable = false, unique = true)
    private String correo;

}
