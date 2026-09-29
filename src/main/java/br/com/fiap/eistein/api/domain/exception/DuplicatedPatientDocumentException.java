package br.com.fiap.eistein.api.domain.exception;

public class DuplicatedPatientDocumentException extends DomainException {

    public DuplicatedPatientDocumentException(String documentValue) {
        super("A patient is already registered with the document %s".formatted(documentValue));
    }
}
