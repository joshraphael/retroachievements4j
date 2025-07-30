package com.joshraphael.retroachievements4j.http;

public class Client {
    private final String host;
    private final String userAgent;

    public Client(String host, String userAgent) {
        this.host = host;
        this.userAgent = userAgent;
    }

    public Request.Builder newRequestBuilder() {
        return new Request.Builder()
                .host(this.host)
                .userAgent(this.userAgent);
    }
}
