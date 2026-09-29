package br.com.fiap.eistein.api.domain.model;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum ExamStatus {

    REQUESTED,
    SCHEDULED,
    COLLECTED,
    UNDER_ANALYSIS,
    RESULT_AVAILABLE,
    CANCELLED,
    NO_SHOW;

    private static final Set<ExamStatus> NO_FURTHER_TRANSITIONS = Collections.emptySet();

    public Set<ExamStatus> allowedTransitions() {
        return switch (this) {
            case REQUESTED -> EnumSet.of(SCHEDULED, CANCELLED);
            case SCHEDULED -> EnumSet.of(COLLECTED, CANCELLED, NO_SHOW);
            case COLLECTED -> EnumSet.of(UNDER_ANALYSIS, CANCELLED);
            case UNDER_ANALYSIS -> EnumSet.of(RESULT_AVAILABLE, CANCELLED);
            case RESULT_AVAILABLE, CANCELLED, NO_SHOW -> NO_FURTHER_TRANSITIONS;
        };
    }

    public boolean canTransitionTo(ExamStatus targetStatus) {
        return allowedTransitions().contains(targetStatus);
    }

    public boolean isFinal() {
        return allowedTransitions().isEmpty();
    }
}
