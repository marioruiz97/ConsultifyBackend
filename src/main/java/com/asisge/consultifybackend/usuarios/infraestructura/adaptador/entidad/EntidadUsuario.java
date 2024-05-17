package com.asisge.consultifybackend.usuarios.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.autenticacion.infraestructura.adaptador.entidad.ModeloAuditoria;
import com.asisge.consultifybackend.usuarios.dominio.modelo.Rol;
import com.asisge.consultifybackend.utilidad.dominio.modelo.ExpresionRegular;
import com.asisge.consultifybackend.utilidad.dominio.modelo.TipoDocumento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class EntidadUsuario extends ModeloAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private
    Long idUsuario;

    // propiedades de contacto del usuario
    @Column(nullable = false, unique = true)
    @NotBlank
    private
    String identificacion;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @NotBlank
    @NotNull
    private
    String nombres;

    @NotBlank
    @NotNull
    private
    String apellidos;

    /**
     * Esta expresión regular se desglosa de la siguiente manera:
     * ^ indica el inicio de la cadena.
     * (60\d{1})\d{7}:
     * 60 es el prefijo para números de telefonía fija.
     * \d{1} coincide con un dígito después del prefijo.
     * \d{7} coincide con exactamente 7 dígitos después del dígito indicador.
     * | se utiliza para indicar una alternativa entre las dos condiciones.
     * ^(3\d{9})$:
     * 3 es el prefijo para números de telefonía móvil.
     * \d{9} coincide con exactamente 9 dígitos después del prefijo.
     * $ indica el final de la cadena.
     */
    @Pattern(regexp = ExpresionRegular.PATRON_TELEFONO)
    @NotBlank
    @NotNull
    private String telefono;

    /**
     * La expresión regular se desglosa de la siguiente manera:
     * ^ indica el inicio de la cadena.
     * <p>[\w\.]+ coincide con uno o más caracteres alfanuméricos, puntos, guiones bajos, porcentajes, signos más y signos menos en la parte local del correo electrónico.</p>
     * <p>@ coincide con el símbolo '@', que separa la parte local del dominio.</p>
     * <p>([\w-]+) coincide con uno o más caracteres alfanuméricos, puntos y guiones en la parte de dominio del correo electrónico.</p>
     * <p>\. coincide con un punto literal, que separa el dominio de nivel superior (TLD) del resto del dominio.</p>
     * <p>[\w-]{2,} coincide con dos o más caracteres alfabéticos en el TLD.</p>
     * $ indica el final de la cadena.
     */
    @NotBlank
    @NotNull
    @Email(regexp = "^[\\w\\.]+@([\\w-]+)\\.+[\\w-]{2,}$", flags = {Pattern.Flag.CASE_INSENSITIVE})
    @Column(nullable = false, unique = true)
    private String correo;

    // propiedades de autenticacion del usuario
    @Column(nullable = false, unique = true, length = 16)
    @NotBlank
    private String nombreUsuario;

    @NotBlank
    @NotNull
    private String contrasena;

    private LocalDateTime ultimoInicio;

    @NotNull
    private Boolean activo;

    @NotNull
    private Boolean verificado;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public EntidadUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
