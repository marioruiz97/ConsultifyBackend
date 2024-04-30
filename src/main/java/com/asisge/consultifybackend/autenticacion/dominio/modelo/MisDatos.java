package com.asisge.consultifybackend.autenticacion.dominio.modelo;

import com.asisge.consultifybackend.usuarios.dominio.modelo.Rol;
import com.asisge.consultifybackend.usuarios.dominio.modelo.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    private Rol rol;
}
