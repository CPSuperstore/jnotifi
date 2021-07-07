package com.cpsuperstore.jnotifi.exceptions;

public class FailedToSubscribeException extends RuntimeException {
    public FailedToSubscribeException(String message) {
        super("Failed to retrieve messages from the server. Reason: " + message);
    }
}
