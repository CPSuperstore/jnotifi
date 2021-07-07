package com.cpsuperstore.jnotifi.exceptions;

public class FailedToPublishException extends RuntimeException {
    public FailedToPublishException(String message) {
        super("The requested message could not be published. Reason: " + message);
    }
}
