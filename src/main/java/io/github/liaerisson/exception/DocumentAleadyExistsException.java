package io.github.liaerisson.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DocumentAleadyExistsException extends RuntimeException {

    public DocumentAleadyExistsException(int id) {
        super("Document with id " + id + " already exists.");
    }
}
