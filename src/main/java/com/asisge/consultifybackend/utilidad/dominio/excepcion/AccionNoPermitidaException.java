package com.asisge.consultifybackend.utilidad.dominio.excepcion;

import lombok.Getter;

@Getter
public class AccionNoPermitidaException extends RuntimeException {

    private final String titulo;
    private final String mensaje;

    public AccionNoPermitidaException(String mensaje) {
        super("Accion no permitida: " + mensaje);
        this.titulo = "Accion no permitida";
        this.mensaje = mensaje;
    }
}
