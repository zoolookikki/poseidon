package com.nnk.springboot.exception;

import java.io.Serial;

/**
 * Any internal error regarding a entity that no longer exists in the database.
 */
    public class EntityNotFoundException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1L;

        public EntityNotFoundException(String message) {
            super(message);
        }
    }
