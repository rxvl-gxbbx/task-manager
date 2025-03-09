package org.rxvlvxr.tasks.http.handler;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.rxvlvxr.tasks.dto.OperationUpdateResult;
import org.rxvlvxr.tasks.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerHandlerException {

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleExceptions(AuthorizationDeniedException e, HttpServletRequest request) {
        log.error("Access denied: [{}] {} - Insufficient permissions to access the resource. Reason: {}",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());

        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        return new ResponseEntity<>(new OperationUpdateResult(httpStatus.value(), null, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleExceptions(JwtException e, HttpServletRequest request) {
        log.error("JWT authentication failed: [{}] {} - Invalid or expired token. Reason: {}",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        return new ResponseEntity<>(new OperationUpdateResult(httpStatus.value(), null, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleExceptions(NotFoundException e, HttpServletRequest request) {
        log.error("Resource not found: [{}] {} - {}",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(new OperationUpdateResult(httpStatus.value(), null, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleExceptions(AuthenticationException e, HttpServletRequest request) {
        log.error("Authentication failed: [{}] {} - Invalid credentials or authentication failure. Reason: {}",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        return new ResponseEntity<>(new OperationUpdateResult(httpStatus.value(), null, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception e, HttpServletRequest request) {
        log.error("Unhandled exception: [{}] {} - {}",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage(),
                e);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new OperationUpdateResult(httpStatus.value(), null, e.getMessage()), httpStatus);
    }
}
