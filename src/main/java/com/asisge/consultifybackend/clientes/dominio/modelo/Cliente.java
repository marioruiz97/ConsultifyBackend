package com.asisge.consultifybackend.clientes.dominio.modelo;

import com.asisge.consultifybackend.utilidad.dominio.modelo.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Setter
    private Long idCliente;
    private String numeroIdentificacion;
    private String nombreComercial;
    private String razonSocial;
    private TipoDocumento tipoDocumento;
    private List<ContactoCliente> contactos;

    public Cliente(Long idCliente, String numeroIdentificacion, String nombreComercial, String razonSocial, TipoDocumento tipoDocumento) {
        this.idCliente = idCliente;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreComercial = nombreComercial;
        this.razonSocial = razonSocial;
        this.tipoDocumento = tipoDocumento;
    }

    public void validarCampos() {
        boolean esValido = this.numeroIdentificacion != null
                && this.nombreComercial !=null
                && this.razonSocial !=null
                && this.tipoDocumento != null;
        if(!esValido) throw new IllegalArgumentException("Por favor revise los campos obligatorios");
    }
}
