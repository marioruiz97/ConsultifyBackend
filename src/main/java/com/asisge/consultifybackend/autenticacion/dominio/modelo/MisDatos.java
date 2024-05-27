package com.asisge.consultifybackend.autenticacion.dominio.modelo;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Rol;
import com.asisge.consultifybackend.utilidad.dominio.modelo.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MisDatos {

    private String identificacion;
    private TipoDocumento tipoDocumento;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String nombreUsuario;
    private String creadoPor;
    private LocalDateTime ultimoInicio;
    private Rol rol;
}
