package com.asisge.consultifybackend.clientes.dominio.modelo;

import com.asisge.consultifybackend.utilidad.dominio.modelo.ExpresionRegular;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ContactoCliente {

    @Setter
    @EqualsAndHashCode.Exclude
    private String id;
    private String nombreCompleto;
    private String telefono;
    private String correo;

    public boolean validarContacto() {
        return this.telefono.matches(ExpresionRegular.PATRON_TELEFONO) &&
                this.correo.matches(ExpresionRegular.PATRON_CORREO);
    }

}
