package br.com.fiap.eistein.api.infrastructure.web.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de contato do paciente")
public record ContactInformationResponse(
        @Schema(description = "Telefone") String phoneNumber,
        @Schema(description = "E-mail") String emailAddress,
        @Schema(description = "Endereço residencial") String residentialAddress) {
}
