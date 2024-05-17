package com.asisge.consultifybackend.clientes.dominio.modelo;

import com.asisge.consultifybackend.utilidad.dominio.modelo.TipoDocumento;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "contactos")
public class Cliente {

    @Setter
    private Long idCliente;
    private String numeroIdentificacion;
    private String nombreComercial;
    private String razonSocial;
    private TipoDocumento tipoDocumento;

    @Getter(value = AccessLevel.NONE)
    private List<ContactoCliente> contactos;

    public Cliente(Long idCliente, String numeroIdentificacion, String nombreComercial, String razonSocial, TipoDocumento tipoDocumento) {
        this.idCliente = idCliente;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreComercial = nombreComercial;
        this.razonSocial = razonSocial;
        this.tipoDocumento = tipoDocumento;
    }

    public List<ContactoCliente> getContactos() {
        if (contactos != null)
            return contactos;
        else return new ArrayList<>();
    }

    public void validarCampos() {
        boolean esValido = this.numeroIdentificacion != null
                && this.nombreComercial != null
                && this.razonSocial != null
                && this.tipoDocumento != null;
        if (!esValido) throw new IllegalArgumentException("Por favor revise los campos obligatorios");
    }
}
