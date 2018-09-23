/**
 * SmartFridgeManagerUnExpectedException
 * M101J
 * <p>
 * Copyright (c) 2018, Apple Inc.
 * All rights reserved.
 */

package com.app.mypack.exception;

public class SmartFridgeManagerUnExpectedException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public SmartFridgeManagerUnExpectedException(String message) {
        super(" \"UNEXPECTED ERROR OCCURRED :: \" + message;"+message);
    }

}
