package com.nnk.springboot.exception;

import java.io.Serial;

/**
 * Any internal error regarding a name that exists in the database.
 */
public class RulenameAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RulenameAlreadyExistsException(String message) {
        super(message);
    }
}
