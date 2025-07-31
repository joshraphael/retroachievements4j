package com.joshraphael.retroachievements4j.http;

import java.net.http.HttpClient;

public class Client {
    private final HttpClient client;
    private final String host;
    private final String userAgent;

    public Client(HttpClient client, String host, String userAgent) {
        this.client = client;
        this.host = host;
        this.userAgent = userAgent;
    }

//    public Request.Builder newRequestBuilder() {
//        return new Request.Builder()
//                .host(this.host)
//                .userAgent(this.userAgent);
//    }
}
