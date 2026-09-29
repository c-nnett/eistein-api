package br.com.fiap.eistein.api.infrastructure.web.response;

import java.time.LocalDateTime;

public record ApiErrorResponse(int status, String error, String message, LocalDateTime occurredAt) {

    public static ApiErrorResponse of(int status, String error, String message) {
        return new ApiErrorResponse(status, error, message, LocalDateTime.now());
    }
}
