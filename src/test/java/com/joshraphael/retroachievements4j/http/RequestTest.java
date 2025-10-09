package com.joshraphael.retroachievements4j.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {
    @Test
    void testBuild() {
        Request request = new Request()
                .host("http://localhost")
                .path("/api/v1/some_resource")
                .methodOPTIONS()
                .methodPOST()
                .methodGET()
                .userAgent("retroachievements4j/v0.0.0")
                .bearerToken("secret_bearer")
                .U("myUsername")
                .Y("secret_token");
        assertEquals("http://localhost", request.getHost());
        assertEquals("/api/v1/some_resource", request.getPath());
        assertEquals("GET", request.getMethod());
        assertEquals(2, request.getHeaders().size());
        assertTrue(request.getHeaders().containsKey("User-Agent"));
        assertTrue(request.getHeaders().containsKey("Authorization"));
        assertEquals("retroachievements4j/v0.0.0", request.getHeaders().get("User-Agent"));
        assertEquals("Bearer secret_bearer", request.getHeaders().get("Authorization"));
        assertEquals(2, request.getQueryParameters().size());
        assertTrue(request.getQueryParameters().containsKey("u"));
        assertTrue(request.getQueryParameters().containsKey("y"));
        assertEquals("myUsername", request.getQueryParameters().get("u"));
        assertEquals("secret_token", request.getQueryParameters().get("y"));
        Assertions.assertDoesNotThrow(() -> {
            HttpRequest r = request.build();
            assertEquals("http", r.uri().getScheme());
            assertEquals("localhost", r.uri().getHost());
            assertEquals("/api/v1/some_resource", r.uri().getPath());
        });
    }
}
