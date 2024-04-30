package com.asisge.consultifybackend.autenticacion.infraestructura.controlador.handler;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.ApiError;
import com.asisge.consultifybackend.autenticacion.infraestructura.excepcion.ViolacionIntegridadException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApiRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_INICIANDO_SESION = "Error iniciando sesión";

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraints(ConstraintViolationException ex, WebRequest request) {
        StringBuilder mensaje = new StringBuilder();
        ex.getConstraintViolations().forEach(err -> {
            String error = err.getPropertyPath().toString() + ": " + err.getMessage() + "\n";
            mensaje.append(error);
        });
        ApiError errorResponse = new ApiError(HttpStatus.BAD_REQUEST.value(), "Hay campos que no cumplen el formato requerido", mensaje.toString());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class, org.hibernate.exception.ConstraintViolationException.class})
    protected ResponseEntity<Object> handleIntegrityExceptions(org.hibernate.exception.ConstraintViolationException ex, WebRequest request) {
        ViolacionIntegridadException exception = new ViolacionIntegridadException(ex);
        ApiError error = new ApiError(HttpStatus.CONFLICT.value(), exception.getTitulo(), exception.getMensaje());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {DataAccessException.class})
    protected ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
        ApiError error = ApiError.conMensajeGenerico(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleIllegalException(RuntimeException ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ERROR_INICIANDO_SESION, "Usuario/contraseña incorrectas");
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {CredentialsExpiredException.class})
    protected ResponseEntity<Object> handleCredentialsExpiredException(CredentialsExpiredException ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ERROR_INICIANDO_SESION, "El token de acceso ha caducado");
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {LockedException.class})
    protected ResponseEntity<Object> handleLockedException(LockedException ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ERROR_INICIANDO_SESION, "Debes validar tu correo electrónico antes de iniciar sesión");
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {DisabledException.class})
    protected ResponseEntity<Object> handleDisabledException(DisabledException ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ERROR_INICIANDO_SESION, "Usuario Inactivo, primero debe activarse");
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

}
