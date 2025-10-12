package com.joshraphael.retroachievements4j.http;

public class BadHttpResponseException extends RuntimeException {
    private final int statusCode;
    public BadHttpResponseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
    public int getStatusCode() {
        return statusCode;
    }
}
