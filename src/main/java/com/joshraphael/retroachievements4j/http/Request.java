package com.joshraphael.retroachievements4j.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.hc.core5.net.URIBuilder;

public class Request {
    private String host;
    private String path;
    private String method;
    private final Map<String, String> queryParameters;
    private final Map<String, String> headers;

    public Request() {
        this.host = "";
        this.path = "";
        this.method = "GET";
        this.queryParameters = new HashMap<>();
        this.headers = new HashMap<>();
    }

    public String getHost() {
        return this.host;
    }

    public Request host(String host) {
        this.host = host;
        return this;
    }

    public String getPath() {
        return this.path;
    }

    public Request path(String path) {
        this.path = path;
        return this;
    }

    public String getMethod() {
        return this.method;
    }

    public Request methodGET() {
        this.method = "GET";
        return this;
    }

    public Request methodOPTIONS() {
        this.method = "OPTIONS";
        return this;
    }

    public Request methodPOST() {
        this.method = "POST";
        return this;
    }

    public Map<String, String> getQueryParameters() {
        return this.queryParameters;
    }

    private Request queryParameter(String key, String value) {
        this.queryParameters.put(key, value);
        return this;
    }

    public Request I(String[] values) {
        return this.queryParameter("i", String.join(",", values));
    }

    public Request P(String value) {
        return this.queryParameter("p", value);
    }

    public Request R(String value) {
        return this.queryParameter("r", value);
    }

    public Request U(String value) {
        return this.queryParameter("u", value);
    }

    public Request Y(String value) {
        return this.queryParameter("y", value);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Request bearerToken(String token) {
        return this.header("Authorization", String.format("Bearer %s", token));
    }

    public Request userAgent(String userAgent) {
        return this.header("User-Agent", userAgent);
    }

    private Request header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public HttpRequest build() throws URISyntaxException {
        URIBuilder builder = new URIBuilder(this.host);
        builder.setPath(this.path);
        for (String param : this.queryParameters.keySet()) {
            builder.addParameter(param, this.queryParameters.get(param));
        }
        URI uri = builder.build();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.uri(uri);
        requestBuilder.version(HttpClient.Version.HTTP_1_1);
        requestBuilder.method(this.method, HttpRequest.BodyPublishers.noBody());
        for (String header : this.headers.keySet()) {
            builder.addParameter(header, this.headers.get(header));
        }
        return requestBuilder.build();
    }
}