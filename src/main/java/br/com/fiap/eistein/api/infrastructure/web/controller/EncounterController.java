package br.com.fiap.eistein.api.infrastructure.web.controller;

import br.com.fiap.eistein.api.application.usecase.encounter.FindEncounterByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.encounter.OpenEncounterUseCase;
import br.com.fiap.eistein.api.application.usecase.encounter.RegisterClinicalNoteUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.ListEncounterExamsUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.RequestExamUseCase;
import br.com.fiap.eistein.api.infrastructure.configuration.OpenApiConfiguration;
import br.com.fiap.eistein.api.infrastructure.web.mapper.EncounterResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.mapper.ExamResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.request.OpenEncounterRequest;
import br.com.fiap.eistein.api.infrastructure.web.request.RegisterClinicalNoteRequest;
import br.com.fiap.eistein.api.infrastructure.web.request.RequestExamRequest;
import br.com.fiap.eistein.api.infrastructure.web.response.ApiErrorResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.EncounterResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.ExamResponse;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/atendimentos")
@Tag(name = OpenApiConfiguration.ENCOUNTERS_TAG)
public class EncounterController {

    private final OpenEncounterUseCase openEncounterUseCase;
    private final FindEncounterByIdentifierUseCase findEncounterByIdentifierUseCase;
    private final RegisterClinicalNoteUseCase registerClinicalNoteUseCase;
    private final RequestExamUseCase requestExamUseCase;
    private final ListEncounterExamsUseCase listEncounterExamsUseCase;

    public EncounterController(
            OpenEncounterUseCase openEncounterUseCase,
            FindEncounterByIdentifierUseCase findEncounterByIdentifierUseCase,
            RegisterClinicalNoteUseCase registerClinicalNoteUseCase,
            RequestExamUseCase requestExamUseCase,
            ListEncounterExamsUseCase listEncounterExamsUseCase) {
        this.openEncounterUseCase = openEncounterUseCase;
        this.findEncounterByIdentifierUseCase = findEncounterByIdentifierUseCase;
        this.registerClinicalNoteUseCase = registerClinicalNoteUseCase;
        this.requestExamUseCase = requestExamUseCase;
        this.listEncounterExamsUseCase = listEncounterExamsUseCase;
    }

    @PostMapping
    @Operation(
            summary = "Abrir atendimento com triagem",
            description = "Abre um atendimento para o paciente no prontuário dele, registrando o motivo "
                    + "de entrada e a classificação de risco da triagem.")
    @ApiResponse(responseCode = "201", description = "Atendimento aberto; o header `Location` aponta para o recurso criado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou campos obrigatórios ausentes",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Paciente, profissional ou prontuário não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<EncounterResponse> openEncounter(
            @RequestBody OpenEncounterRequest request, UriComponentsBuilder uriComponentsBuilder) {
        EncounterResponse response =
                EncounterResponseMapper.toResponse(openEncounterUseCase.execute(request.toCommand()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/v1/atendimentos/{id}").build(response.identifier()))
                .body(response);
    }

    @GetMapping("/{encounterIdentifier}")
    @Operation(summary = "Consultar atendimento", description = "Retorna o atendimento com todas as evoluções clínicas.")
    @ApiResponse(responseCode = "200", description = "Atendimento encontrado")
    @ApiResponse(responseCode = "404", description = "Atendimento não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public EncounterResponse findEncounterByIdentifier(
            @Parameter(description = "Identificador do atendimento") @PathVariable UUID encounterIdentifier) {
        return EncounterResponseMapper.toResponse(findEncounterByIdentifierUseCase.execute(encounterIdentifier));
    }

    @PostMapping("/{encounterIdentifier}/evolucoes")
    @Operation(
            summary = "Registrar evolução clínica",
            description = "Adiciona uma evolução clínica ao atendimento. Quando `outcome` é informado, "
                    + "o desfecho do atendimento é atualizado.")
    @ApiResponse(responseCode = "200", description = "Atendimento atualizado com a nova evolução")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou campos obrigatórios ausentes",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Atendimento ou profissional não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public EncounterResponse registerClinicalNote(
            @Parameter(description = "Identificador do atendimento") @PathVariable UUID encounterIdentifier,
            @RequestBody RegisterClinicalNoteRequest request) {
        return EncounterResponseMapper.toResponse(
                registerClinicalNoteUseCase.execute(request.toCommand(encounterIdentifier)));
    }

    @PostMapping("/{encounterIdentifier}/exames")
    @Operation(
            summary = "Solicitar exame no atendimento",
            description = "Registra um pedido de exame vinculado ao atendimento. O exame nasce com status "
                    + "`REQUESTED` e recebe um número de protocolo.")
    @ApiResponse(responseCode = "201", description = "Exame solicitado; o header `Location` aponta para o recurso criado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou campos obrigatórios ausentes",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Atendimento ou profissional solicitante não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<ExamResponse> requestExam(
            @Parameter(description = "Identificador do atendimento") @PathVariable UUID encounterIdentifier,
            @RequestBody RequestExamRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        ExamResponse response =
                ExamResponseMapper.toResponse(requestExamUseCase.execute(request.toCommand(encounterIdentifier)));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/v1/exames/{id}").build(response.identifier()))
                .body(response);
    }

    @GetMapping("/{encounterIdentifier}/exames")
    @Operation(summary = "Listar exames pedidos no atendimento")
    @ApiResponse(responseCode = "200", description = "Exames solicitados no atendimento")
    @ApiResponse(responseCode = "404", description = "Atendimento não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public List<ExamResponse> listEncounterExams(
            @Parameter(description = "Identificador do atendimento") @PathVariable UUID encounterIdentifier) {
        return listEncounterExamsUseCase.execute(encounterIdentifier).stream()
                .map(ExamResponseMapper::toResponse)
                .toList();
    }
}
