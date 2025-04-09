package com.nnk.springboot.exception;

import java.io.Serial;

/**
 * Any internal error regarding a name that exists in the database.
 */
public class RuleNameAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RuleNameAlreadyExistsException(String message) {
        super(message);
    }
}
