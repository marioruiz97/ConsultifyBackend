package com.asisge.consultifybackend.clientes.dominio.modelo;

import com.asisge.consultifybackend.utilidad.dominio.modelo.ExpresionRegular;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContactoCliente {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String nombreCompleto;
    private String telefono;
    private String correo;

    public boolean validarContacto() {
        return this.telefono.matches(ExpresionRegular.PATRON_TELEFONO) &&
                this.correo.matches(ExpresionRegular.PATRON_CORREO);
    }

}
