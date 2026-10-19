package fi.metropolia.arazm.demo.common.exceptions;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<Map<String, String>> handleResponseStatus(
                        ResponseStatusException ex) {

                HttpStatus status = HttpStatus.valueOf(
                                ex.getStatusCode().value());

                return ResponseEntity.status(status).body(Map.of(
                                "status", String.valueOf(status.value()),
                                "error", status.getReasonPhrase(),
                                "message", ex.getReason() != null
                                                ? ex.getReason()
                                                : "Request failed"));
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Map<String, String>> handleInvalidJson(
                        HttpMessageNotReadableException ex) {

                return ResponseEntity.badRequest().body(Map.of(
                                "status", "400",
                                "error", "Bad Request",
                                "message", "Invalid request body or enum value"));
        }

        @ExceptionHandler(OptimisticLockingFailureException.class)
        public ResponseEntity<Map<String, String>> handleOptimisticLock(
                        OptimisticLockingFailureException ex) {

                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                                "status", "409",
                                "error", "Conflict",
                                "message", "The record was modified by another request"));
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<Map<String, String>> handleDataIntegrity(
                        DataIntegrityViolationException ex) {

                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                                "status", "409",
                                "error", "Conflict",
                                "message", "The operation conflicts with existing data"));
        }


        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidation(
                MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": "
                        + error.getDefaultMessage())
                .distinct()
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(Map.of(
                "status", "400",
                "error", "Bad Request",
                "message", message.isBlank()
                        ? "Validation failed"
                        : message
        ));
        }
}