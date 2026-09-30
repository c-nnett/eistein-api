package br.com.fiap.eistein.api.infrastructure.web.controller;

import br.com.fiap.eistein.api.application.usecase.exam.AdvanceExamStatusUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.FindExamByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.ReleaseExamResultUseCase;
import br.com.fiap.eistein.api.infrastructure.configuration.OpenApiConfiguration;
import br.com.fiap.eistein.api.infrastructure.web.mapper.ExamResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.request.AdvanceExamStatusRequest;
import br.com.fiap.eistein.api.infrastructure.web.request.ReleaseExamResultRequest;
import br.com.fiap.eistein.api.infrastructure.web.response.ApiErrorResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.ExamResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exames")
@Tag(name = OpenApiConfiguration.EXAMS_TAG)
public class ExamController {

    private final FindExamByIdentifierUseCase findExamByIdentifierUseCase;
    private final AdvanceExamStatusUseCase advanceExamStatusUseCase;
    private final ReleaseExamResultUseCase releaseExamResultUseCase;

    public ExamController(
            FindExamByIdentifierUseCase findExamByIdentifierUseCase,
            AdvanceExamStatusUseCase advanceExamStatusUseCase,
            ReleaseExamResultUseCase releaseExamResultUseCase) {
        this.findExamByIdentifierUseCase = findExamByIdentifierUseCase;
        this.advanceExamStatusUseCase = advanceExamStatusUseCase;
        this.releaseExamResultUseCase = releaseExamResultUseCase;
    }

    @GetMapping("/{examIdentifier}")
    @Operation(
            summary = "Consultar exame e sua linha do tempo",
            description = "Retorna o exame com o status atual, os próximos status permitidos, "
                    + "o histórico de mudanças de status e o resultado, quando liberado.")
    @ApiResponse(responseCode = "200", description = "Exame encontrado")
    @ApiResponse(responseCode = "404", description = "Exame não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ExamResponse findExamByIdentifier(
            @Parameter(description = "Identificador do exame") @PathVariable UUID examIdentifier) {
        return ExamResponseMapper.toResponse(findExamByIdentifierUseCase.execute(examIdentifier));
    }

    @PatchMapping("/{examIdentifier}/status")
    @Operation(
            summary = "Avançar status do exame",
            description = """
                    Move o exame para o próximo status. Transições permitidas:

                    | Status atual | Próximos status |
                    |---|---|
                    | `REQUESTED` | `SCHEDULED`, `CANCELLED` |
                    | `SCHEDULED` | `COLLECTED`, `CANCELLED`, `NO_SHOW` |
                    | `COLLECTED` | `UNDER_ANALYSIS`, `CANCELLED` |
                    | `UNDER_ANALYSIS` | `RESULT_AVAILABLE`, `CANCELLED` |
                    | `RESULT_AVAILABLE`, `CANCELLED`, `NO_SHOW` | nenhum (status final) |

                    Para liberar o resultado com laudo, prefira `POST /api/v1/exames/{examIdentifier}/resultado`.""")
    @ApiResponse(responseCode = "200", description = "Status do exame atualizado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou campos obrigatórios ausentes",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Exame não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Transição de status não permitida a partir do status atual",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ExamResponse advanceExamStatus(
            @Parameter(description = "Identificador do exame") @PathVariable UUID examIdentifier,
            @RequestBody AdvanceExamStatusRequest request) {
        return ExamResponseMapper.toResponse(advanceExamStatusUseCase.execute(request.toCommand(examIdentifier)));
    }

    @PostMapping("/{examIdentifier}/resultado")
    @Operation(
            summary = "Liberar resultado do exame",
            description = "Registra o resultado e o laudo do exame e o move para `RESULT_AVAILABLE`. "
                    + "Só é permitido quando o exame está em `UNDER_ANALYSIS`.")
    @ApiResponse(responseCode = "200", description = "Resultado liberado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou campos obrigatórios ausentes",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Exame não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "O exame não está em análise",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ExamResponse releaseExamResult(
            @Parameter(description = "Identificador do exame") @PathVariable UUID examIdentifier,
            @RequestBody ReleaseExamResultRequest request) {
        return ExamResponseMapper.toResponse(releaseExamResultUseCase.execute(request.toCommand(examIdentifier)));
    }
}
