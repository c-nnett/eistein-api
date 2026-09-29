package br.com.fiap.eistein.api.infrastructure.web.controller;

import br.com.fiap.eistein.api.application.usecase.exam.AdvanceExamStatusUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.FindExamByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.ReleaseExamResultUseCase;
import br.com.fiap.eistein.api.infrastructure.web.mapper.ExamResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.request.AdvanceExamStatusRequest;
import br.com.fiap.eistein.api.infrastructure.web.request.ReleaseExamResultRequest;
import br.com.fiap.eistein.api.infrastructure.web.response.ExamResponse;
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
    public ExamResponse findExamByIdentifier(@PathVariable UUID examIdentifier) {
        return ExamResponseMapper.toResponse(findExamByIdentifierUseCase.execute(examIdentifier));
    }

    @PatchMapping("/{examIdentifier}/status")
    public ExamResponse advanceExamStatus(
            @PathVariable UUID examIdentifier, @RequestBody AdvanceExamStatusRequest request) {
        return ExamResponseMapper.toResponse(advanceExamStatusUseCase.execute(request.toCommand(examIdentifier)));
    }

    @PostMapping("/{examIdentifier}/resultado")
    public ExamResponse releaseExamResult(
            @PathVariable UUID examIdentifier, @RequestBody ReleaseExamResultRequest request) {
        return ExamResponseMapper.toResponse(releaseExamResultUseCase.execute(request.toCommand(examIdentifier)));
    }
}
