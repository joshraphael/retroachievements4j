package com.joshraphael.retroachievements4j.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ProtocolVersion;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.net.URIBuilder;

public class Request {
    private String host;
    private String path;
    private String method;
    private final Map<String, String> queryParameters;
    private final Map<String, String> headers;
    private final Map<String, String> formParts;

    public Request() {
        this.host = "";
        this.path = "";
        this.method = "GET";
        this.queryParameters = new HashMap<>();
        this.headers = new HashMap<>();
        this.formParts = new HashMap<>();
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

    public Request G(int value) {
        String gameID = Integer.toString(value);
        return this.queryParameter("g", gameID);
    }

    public Request I(String[] values) {
        return this.queryParameter("i", String.join(",", values));
    }

    public Request K(String value) {
        return this.queryParameter("k", value);
    }

    public Request P(String value) {
        return this.queryParameter("p", value);
    }

    public Request R(String value) {
        return this.queryParameter("r", value);
    }

    public Request T(String value) {
        return this.queryParameter("t", value);
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

    public Map<String, String> getFormParts() {
        return this.formParts;
    }

    public Request formPart(String key, String value) {
        this.formParts.put(key, value);
        return this;
    }

    public ClassicHttpRequest build() throws URISyntaxException {
        URIBuilder builder = new URIBuilder(this.host);
        builder.setPath(this.path);
        for (String param : this.queryParameters.keySet()) {
            builder.addParameter(param, this.queryParameters.get(param));
        }
        URI uri = builder.build();
        ProtocolVersion http11 = new ProtocolVersion("HTTP", 1, 1);
        ClassicRequestBuilder reqBuilder = ClassicRequestBuilder.create(this.method).setUri(uri).setVersion(http11);
        for (String header : this.headers.keySet()) {
            reqBuilder.addHeader(header, this.headers.get(header));
        }
        if(!this.formParts.isEmpty()) {
            MultipartEntityBuilder multiPartForm = MultipartEntityBuilder.create();
            for (String key : this.formParts.keySet()) {
                multiPartForm.addTextBody(key, this.formParts.get(key));
            }
            reqBuilder.setEntity(multiPartForm.build());
        }
        return reqBuilder.build();
    }
}