package br.com.fiap.eistein.api.infrastructure.web.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Corpo padrão das respostas de erro")
public record ApiErrorResponse(
        @Schema(description = "Código HTTP", example = "404") int status,
        @Schema(description = "Descrição do código HTTP", example = "Not Found") String error,
        @Schema(description = "Mensagem detalhando o erro") String message,
        @Schema(description = "Momento em que o erro ocorreu") LocalDateTime occurredAt) {

    public static ApiErrorResponse of(int status, String error, String message) {
        return new ApiErrorResponse(status, error, message, LocalDateTime.now());
    }
}
