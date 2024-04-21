package com.asisge.consultifybackend.usuarios.dominio.modelo;

import lombok.Getter;

public enum TipoDocumento {
    CC("Cédula de Ciudadanía"),
    CE("Cédula de extranjería"),
    PA("Pasaporte"),
    NIT("NIT"),
    TI("Tarjeta de identidad");

    @Getter
    private final String tipo;

    TipoDocumento(String tipo) {
        this.tipo=tipo;
    }
}
