package com.joshraphael.retroachievements4j.http;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {
    @Test
    void testBuilder() {
        Request request = new Request.Builder()
                .host("http://localhost")
                .path("/api/v1/some_resource")
                .methodGET()
                .userAgent("retroachievements4j/v0.0.0")
                .bearerToken("secret_bearer")
                .build();
        assertEquals("http://localhost", request.getHost());
        assertEquals("/api/v1/some_resource", request.getPath());
        assertEquals("GET", request.getMethod());
        assertEquals(2, request.getHeaders().size());
        assertTrue(request.getHeaders().containsKey("User-Agent"));
        assertTrue(request.getHeaders().containsKey("Authorization"));
        assertEquals("retroachievements4j/v0.0.0", request.getHeaders().get("User-Agent"));
        assertEquals("Bearer secret_bearer", request.getHeaders().get("Authorization"));
    }
}
