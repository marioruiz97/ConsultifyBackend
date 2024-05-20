package com.asisge.consultifybackend.autenticacion.infraestructura.controlador.handler;

import com.asisge.consultifybackend.autenticacion.aplicacion.dto.ApiError;
import com.asisge.consultifybackend.utilidad.dominio.excepcion.AccionNoPermitidaException;
import com.asisge.consultifybackend.utilidad.dominio.excepcion.ViolacionIntegridadException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;


@ControllerAdvice
public class ApiRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_INICIANDO_SESION = "Error iniciando sesión";

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraints(ConstraintViolationException ex, WebRequest request) {
        printInfoError(ex);

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
        printInfoError(ex);

        ViolacionIntegridadException exception = new ViolacionIntegridadException(ex);
        ApiError error = new ApiError(HttpStatus.CONFLICT.value(), exception.getTitulo(), exception.getMensaje());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {DataAccessException.class})
    protected ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
        printInfoError(ex);

        ApiError error = ApiError.conMensajeGenerico(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleIllegalException(RuntimeException ex, WebRequest request) {
        printInfoError(ex);

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
        printInfoError(ex);

        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        printInfoError(ex);

        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage(), ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        printInfoError(ex);

        String titulo = "Error: Recurso no encontrado (" + request.getContextPath() + ") " + ex.getLocalizedMessage();
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), titulo, ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {AccionNoPermitidaException.class})
    protected ResponseEntity<Object> handleAccionNoPermitidaException(AccionNoPermitidaException ex, WebRequest request) {
        printInfoError(ex);

        ApiError error = new ApiError(HttpStatus.FORBIDDEN.value(), ex.getTitulo(), ex.getMensaje());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        printInfoError(ex);

        ApiError error = new ApiError(HttpStatus.FORBIDDEN.value(),
                "Acceso denegado",
                "No tienes acceso a este recurso" + request.getContextPath());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        printInfoError(ex);

        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ERROR_INICIANDO_SESION, "Usuario/contraseña incorrectas");
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {CredentialsExpiredException.class})
    protected ResponseEntity<Object> handleCredentialsExpiredException(CredentialsExpiredException ex, WebRequest request) {
        printInfoError(ex);

        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ERROR_INICIANDO_SESION, "El token de acceso ha caducado");
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {LockedException.class})
    protected ResponseEntity<Object> handleLockedException(LockedException ex, WebRequest request) {
        printInfoError(ex);

        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ERROR_INICIANDO_SESION, "Debes validar tu correo electrónico antes de iniciar sesión");
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {DisabledException.class})
    protected ResponseEntity<Object> handleDisabledException(DisabledException ex, WebRequest request) {
        printInfoError(ex);

        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), ERROR_INICIANDO_SESION, "Usuario Inactivo, primero debe activarse");
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    private void printInfoError(Exception ex) {
        String mensaje = String.format("Error: %s", ex.getMessage());
        logger.error(mensaje, ex);
    }

}
