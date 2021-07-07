package com.cpsuperstore.jnotifi.exceptions;

import java.util.Arrays;

public class FailedToConfirmMessageException extends RuntimeException {
    public FailedToConfirmMessageException(String message, String id) {
        super("Failed to confirm message ID: " + id + ". Reason: " + message);
    }
}
