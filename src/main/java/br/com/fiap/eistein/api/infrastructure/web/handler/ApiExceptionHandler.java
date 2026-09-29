package br.com.fiap.eistein.api.infrastructure.web.handler;

import br.com.fiap.eistein.api.domain.exception.DuplicatedPatientDocumentException;
import br.com.fiap.eistein.api.domain.exception.DuplicatedProfessionalRegistrationException;
import br.com.fiap.eistein.api.domain.exception.IllegalExamStatusTransitionException;
import br.com.fiap.eistein.api.domain.exception.InvalidDomainDataException;
import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.infrastructure.web.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException exception) {
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(InvalidDomainDataException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidDomainData(InvalidDomainDataException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(IllegalExamStatusTransitionException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalExamStatusTransition(
            IllegalExamStatusTransitionException exception) {
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler({DuplicatedPatientDocumentException.class, DuplicatedProfessionalRegistrationException.class})
    public ResponseEntity<ApiErrorResponse> handleDuplicatedRegistration(RuntimeException exception) {
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidRequestPayload(MethodArgumentNotValidException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(ApiErrorResponse.of(status.value(), status.getReasonPhrase(), message));
    }
}
