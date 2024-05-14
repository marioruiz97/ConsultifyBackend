package com.asisge.consultifybackend.utilidad.dominio.excepcion;

import lombok.Getter;
import org.hibernate.exception.ConstraintViolationException;

@Getter
public class ViolacionIntegridadException extends Exception {

    private final String titulo;

    private final String mensaje;

    public ViolacionIntegridadException(String message) {
        super(message);
        this.titulo = "Error de integridad de datos";
        this.mensaje = message;
    }

    public ViolacionIntegridadException(ConstraintViolationException ex) {
        this.titulo = ex.getLocalizedMessage().split(":")[0].split("\\[")[1];

        String mensajeError = ex.getConstraintName() != null
                ? ex.getConstraintName().split("\\)")[0].replace("\"", "") + ")"
                : "Violación de integridad de datos. Por favor valida los datos ingresados";

        if (mensajeError.toUpperCase().contains("CORREO"))
            mensajeError = "El correo ya está registrado";
        if (mensajeError.toUpperCase().contains("IDENTIFICACION"))
            mensajeError = "El número de identificación ya está registrado";
        if (mensajeError.toUpperCase().contains("NOMBRE_USUARIO"))
            mensajeError = "El nombre de usuario ya está registrado";
        if (mensajeError.toUpperCase().contains("MIEMBRO_PROYECTO(ID_PROYECTO"))
            mensajeError = "Miembro duplicado, ya existe dentro del proyecto";

        this.mensaje = mensajeError;
    }
}
