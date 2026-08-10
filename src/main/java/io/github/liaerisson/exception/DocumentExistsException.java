package io.github.liaerisson.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DocumentExistsException extends RuntimeException {

    public DocumentExistsException(int id) {
        super("Document with id " + id + " already exists.");
    }
}
