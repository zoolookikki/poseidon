package com.nnk.springboot.exception;

import java.io.Serial;

/**
 * Any internal error regarding a username that exists in the database.
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
