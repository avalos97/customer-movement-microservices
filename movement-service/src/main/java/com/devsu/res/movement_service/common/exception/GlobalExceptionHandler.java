package com.devsu.res.movement_service.common.exception;

import java.time.Instant;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsu.res.movement_service.common.constant.ApiStatus;
import com.devsu.res.movement_service.common.constant.ErrorCode;
import com.devsu.res.movement_service.common.response.ApiResponse;
import com.devsu.res.movement_service.common.response.ErrorDetails;
import com.devsu.res.movement_service.domain.exception.CuentaNotFoundException;
import com.devsu.res.movement_service.domain.exception.DomainException;
import com.devsu.res.movement_service.domain.exception.MovimientoNotFoundException;
import com.devsu.res.movement_service.domain.exception.SaldoNoDisponibleException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CuentaNotFoundException.class, MovimientoNotFoundException.class})
    public ResponseEntity<ApiResponse<ErrorDetails>> handleNotFoundException(DomainException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                ex.getErrorCode() != null ? ex.getErrorCode().getErrCode() : ErrorCode.RESOURCE_NOT_FOUND.getErrCode(),
                request.getRequestURI(),
                request.getMethod(),
                Instant.now()
        );
        ApiResponse<ErrorDetails> response = new ApiResponse<>(errorDetails, ex.getMessage(), ApiStatus.NOT_FOUND.name());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SaldoNoDisponibleException.class)
    public ResponseEntity<ApiResponse<ErrorDetails>> handleSaldoNoDisponibleException(SaldoNoDisponibleException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                ex.getErrorCode() != null ? ex.getErrorCode().getErrCode() : ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getErrCode(),
                request.getRequestURI(),
                request.getMethod(),
                Instant.now()
        );
        ApiResponse<ErrorDetails> response = new ApiResponse<>(errorDetails, ex.getMessage(), ApiStatus.BAD_REQUEST.name());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiResponse<ErrorDetails>> handleDomainException(DomainException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                ErrorCode.GENERIC_ERROR.getErrCode(),
                request.getRequestURI(),
                request.getMethod(),
                Instant.now()
        );
        ApiResponse<ErrorDetails> response = new ApiResponse<>(errorDetails, ex.getMessage(), ApiStatus.BAD_REQUEST.name());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

        @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorDetails>> handleValidationException(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        StringBuilder errorMsg = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMsg.append("Error de entrada en el campo ")
                    .append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        });
        // Se envuelve el mensaje en una GenericException con código "400"
        ErrorDetails errorDetails = new ErrorDetails(
                ErrorCode.CONSTRAINT_VIOLATION.getErrCode(),
                request.getRequestURI(),
                request.getMethod(),
                Instant.now());
        ApiResponse<ErrorDetails> response = new ApiResponse<>(errorDetails, errorMsg.toString(),
                ApiStatus.BAD_REQUEST.name());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<ErrorDetails>> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                ErrorCode.GENERIC_ALREADY_EXISTS.getErrCode(),
                request.getRequestURI(),
                request.getMethod(),
                Instant.now());
        ApiResponse<ErrorDetails> response = new ApiResponse<>(errorDetails, ex.getMostSpecificCause().getMessage(),
                ApiStatus.BAD_REQUEST.name());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorDetails>> handleGeneralException(Exception ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                ErrorCode.GENERIC_ERROR.getErrCode(),
                request.getRequestURI(),
                request.getMethod(),
                Instant.now()
        );
        ApiResponse<ErrorDetails> response = new ApiResponse<>(errorDetails, ex.getMessage(), ApiStatus.INTERNAL_SERVER_ERROR.name());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}