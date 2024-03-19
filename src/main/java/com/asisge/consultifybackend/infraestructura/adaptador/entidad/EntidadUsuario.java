package com.asisge.consultifybackend.infraestructura.adaptador.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
     * [a-zA-Z0-9._%+-]+ coincide con uno o más caracteres alfanuméricos, puntos, guiones bajos, porcentajes, signos más y signos menos en la parte local del correo electrónico.
     *
     * @ coincide con el símbolo '@', que separa la parte local del dominio.
     * [a-zA-Z0-9.-]+ coincide con uno o más caracteres alfanuméricos, puntos y guiones en la parte de dominio del correo electrónico.
     * \. coincide con un punto literal, que separa el dominio de nivel superior (TLD) del resto del dominio.
     * [a-zA-Z]{2,} coincide con dos o más caracteres alfabéticos en el TLD.
     * $ indica el final de la cadena.
     */
    @NotBlank
    @NotNull
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", flags = {Pattern.Flag.CASE_INSENSITIVE})
    private String correo;

    // propiedades de autenticacion del usuario
    @Column(nullable = false, unique = true, length = 16)
    @NotBlank
    private String nombreUsuario;

    /**
     * La expresión regular se desglosa de la siguiente manera:
     * ^ indica el inicio de la cadena.
     * (?=.*[A-Z]) verifica que la contraseña contenga al menos una letra mayúscula.
     * (?=.*[a-z]) verifica que la contraseña contenga al menos una letra minúscula.
     * (?=.*\d) verifica que la contraseña contenga al menos un dígito.
     * (?=.*[@$!%*?&]) verifica que la contraseña contenga al menos un carácter especial de la lista proporcionada (@, $, !, %, *, ?, &).
     * [A-Za-z\d@$!%*?&]{8,} coincide con caracteres alfanuméricos y especiales, con un mínimo de 8 caracteres de longitud.
     * $ indica el final de la cadena.
     */
    @NotBlank
    @NotNull
    @Size(min = 8, max = 16)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$")
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

}
