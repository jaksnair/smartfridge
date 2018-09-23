/**
 * ItemCannotBeAddedException
 * M101J
 * <p>
 * Copyright (c) 2018, Apple Inc.
 * All rights reserved.
 */

package com.app.mypack.exception;

public class ItemCannotBeAddedException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ItemCannotBeAddedException(String message) {
        super("\"ITEM TYPE AND CONTAINER TYPE MISMATCH :: \" + message"+message);
    }
}
