package com.asisge.consultifybackend.informes.dominio.modelo;

import com.asisge.consultifybackend.utilidad.dominio.modelo.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ClienteInforme {

    private Long idCliente;
    private TipoDocumento tipoDocumento;
    private String numeroIdentificacion;
    private String nombreComercial;
    private String razonSocial;

}
