package com.asisge.consultifybackend.infraestructura.adaptador.entidad;

import com.asisge.consultifybackend.dominio.modelo.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntidadUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private
    Long idUsuario;

    // propiedades de contacto del usuario
    @Column(nullable = false, unique = true)
    @NotBlank
    private
    String identificacion;

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
    @Pattern(regexp = "^(60\\d)\\d{7}$|^(3\\d{9})$")
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
    private String correo;

    // propiedades de autenticacion del usuario
    @Column(nullable = false, unique = true, length = 16)
    @NotBlank
    private String nombreUsuario;

    @NotBlank
    @NotNull
    private String contrasena;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime creadoEn;

    @Column(nullable = false)
    private Long creadoPor;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime ultimoInicio;

    @NotNull
    private Boolean activo;

    @NotNull
    private Boolean verificado;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Rol rol;

}