package br.com.fiap.eistein.api.domain.exception;

import br.com.fiap.eistein.api.domain.model.ExamStatus;

public class IllegalExamStatusTransitionException extends DomainException {

    public IllegalExamStatusTransitionException(ExamStatus currentStatus, ExamStatus targetStatus) {
        super("An exam cannot move from %s to %s".formatted(currentStatus, targetStatus));
    }
}
