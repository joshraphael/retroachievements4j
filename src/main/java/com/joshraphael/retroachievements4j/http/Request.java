package com.joshraphael.retroachievements4j.http;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private final String host;
    private final String path;
    private final String method;
    private final Map<String, String> queryParameters;
    private final Map<String, String> headers;

    public Request(Builder builder) {
        this.host = builder.host;
        this.path = builder.path;
        this.method = builder.method;
        this.queryParameters = builder.queryParameters;
        this.headers = builder.headers;
    }

    public String getHost() {
        return this.host;
    }

    public String getPath() {
        return this.path;
    }

    public String getMethod() {
        return this.method;
    }

    public Map<String, String> getQueryParameters() {
        return this.queryParameters;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public static class Builder {
        private String host;
        private String path;
        private String method;
        private Map<String, String> queryParameters;
        private Map<String, String> headers;

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder methodGET() {
            this.method = "GET";
            return this;
        }

        public Builder methodOPTIONS() {
            this.method = "OPTIONS";
            return this;
        }

        private Builder queryParameter(String key, String value) {
            if(this.queryParameters == null) {
                this.queryParameters = new HashMap<>();
            }
            this.queryParameters.put(key, value);
            return this;
        }

        public Builder U(String value) {
            return this.queryParameter("u", value);
        }

        public Builder Y(String value) {
            return this.queryParameter("y", value);
        }

        public Builder bearerToken(String token) {
            return this.header("Authorization", String.format("Bearer %s", token));
        }

        public Builder userAgent(String userAgent) {
            return this.header("User-Agent", userAgent);
        }

        private Builder header(String key, String value) {
            if(this.headers == null) {
                this.headers = new HashMap<>();
            }
            this.headers.put(key, value);
            return this;
        }

        public Request build() {
            if(this.method == null || this.method.isBlank()) {
                this.method = "GET";
            }
            return new Request(this);
        }
    }
}
