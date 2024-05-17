package com.asisge.consultifybackend.utilidad.dominio.modelo;

import lombok.Getter;

@Getter
public enum TipoDocumento {
    CC("Cédula de Ciudadanía"),
    CE("Cédula de extranjería"),
    PA("Pasaporte"),
    NIT("NIT"),
    TI("Tarjeta de identidad");

    private final String tipo;

    TipoDocumento(String tipo) {
        this.tipo=tipo;
    }
}
