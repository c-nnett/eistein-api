package br.com.fiap.eistein.api.infrastructure.web.controller;

import br.com.fiap.eistein.api.application.usecase.professional.FindHealthcareProfessionalByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.professional.RegisterHealthcareProfessionalUseCase;
import br.com.fiap.eistein.api.infrastructure.configuration.OpenApiConfiguration;
import br.com.fiap.eistein.api.infrastructure.web.mapper.HealthcareProfessionalResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.request.RegisterHealthcareProfessionalRequest;
import br.com.fiap.eistein.api.infrastructure.web.response.ApiErrorResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.HealthcareProfessionalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profissionais")
@Tag(name = OpenApiConfiguration.PROFESSIONALS_TAG)
public class HealthcareProfessionalController {

    private final RegisterHealthcareProfessionalUseCase registerHealthcareProfessionalUseCase;
    private final FindHealthcareProfessionalByIdentifierUseCase findHealthcareProfessionalByIdentifierUseCase;

    public HealthcareProfessionalController(
            RegisterHealthcareProfessionalUseCase registerHealthcareProfessionalUseCase,
            FindHealthcareProfessionalByIdentifierUseCase findHealthcareProfessionalByIdentifierUseCase) {
        this.registerHealthcareProfessionalUseCase = registerHealthcareProfessionalUseCase;
        this.findHealthcareProfessionalByIdentifierUseCase = findHealthcareProfessionalByIdentifierUseCase;
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar profissional de saúde",
            description = "Cadastra um profissional identificado pelo conselho de classe e número de registro.")
    @ApiResponse(responseCode = "201", description = "Profissional cadastrado; o header `Location` aponta para o recurso criado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou campos obrigatórios ausentes",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Já existe profissional com o mesmo conselho e registro",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<HealthcareProfessionalResponse> registerHealthcareProfessional(
            @RequestBody RegisterHealthcareProfessionalRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        HealthcareProfessionalResponse response = HealthcareProfessionalResponseMapper.toResponse(
                registerHealthcareProfessionalUseCase.execute(request.toCommand()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/v1/profissionais/{id}").build(response.identifier()))
                .body(response);
    }

    @GetMapping("/{professionalIdentifier}")
    @Operation(summary = "Consultar profissional de saúde por identificador")
    @ApiResponse(responseCode = "200", description = "Profissional encontrado")
    @ApiResponse(responseCode = "404", description = "Profissional não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public HealthcareProfessionalResponse findHealthcareProfessionalByIdentifier(
            @Parameter(description = "Identificador do profissional") @PathVariable UUID professionalIdentifier) {
        return HealthcareProfessionalResponseMapper.toResponse(
                findHealthcareProfessionalByIdentifierUseCase.execute(professionalIdentifier));
    }
}
