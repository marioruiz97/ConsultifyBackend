package com.asisge.consultifybackend.clientes.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.utilidad.dominio.modelo.ExpresionRegular;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "contacto_cliente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntidadContactoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nombreCompleto;

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
