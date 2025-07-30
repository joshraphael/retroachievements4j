package com.joshraphael.retroachievements4j.http;

public class Request {
    private final String host;
    private final String path;

    public Request(Builder builder) {
        this.host = builder.host;
        this.path = builder.path;
    }

    public String getHost() {
        return this.host;
    }

    public String getPath() {
        return this.path;
    }

    public static class Builder {
        private String host;
        private String path;

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
