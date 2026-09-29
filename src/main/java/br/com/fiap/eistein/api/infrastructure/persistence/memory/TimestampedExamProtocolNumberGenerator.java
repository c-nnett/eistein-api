package br.com.fiap.eistein.api.infrastructure.persistence.memory;

import br.com.fiap.eistein.api.domain.service.ExamProtocolNumberGenerator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TimestampedExamProtocolNumberGenerator implements ExamProtocolNumberGenerator {

    private static final DateTimeFormatter DATE_PREFIX_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String PROTOCOL_TEMPLATE = "EXM-%s-%06d";

    private final AtomicLong protocolSequence = new AtomicLong();

    @Override
    public String generate() {
        return PROTOCOL_TEMPLATE.formatted(
                LocalDate.now().format(DATE_PREFIX_FORMATTER), protocolSequence.incrementAndGet());
    }
}
