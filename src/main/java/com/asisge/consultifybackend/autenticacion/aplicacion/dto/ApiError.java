package com.asisge.consultifybackend.autenticacion.aplicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public class ApiError {

    private int status;
    private String error;
    private String message;

    public static ApiError conMensajeGenerico(Exception ex, HttpStatus status) {
        return new ApiError(
                status.value(),
                ex.getLocalizedMessage(),
                ex.getCause().getLocalizedMessage());
    }

    public static ApiError conMensajeGenerico(NestedRuntimeException ex, HttpStatus status) {
        return new ApiError(
                status.value(),
                ex.getLocalizedMessage(),
                ex.getMostSpecificCause().getLocalizedMessage());
    }
}