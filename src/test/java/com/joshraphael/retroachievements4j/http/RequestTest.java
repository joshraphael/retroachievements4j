package com.joshraphael.retroachievements4j.http;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.junit.jupiter.api.Test;

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
                .G(32)
                .I(new String[]{"133", "42"})
                .P("secret_password")
                .R("login2")
                .T("secret_token1")
                .U("myUsername")
                .Y("secret_token2")
                .formPart("state", "good");
        assertEquals("http://localhost", request.getHost());
        assertEquals("/api/v1/some_resource", request.getPath());
        assertEquals("GET", request.getMethod());
        assertEquals(2, request.getHeaders().size());
        assertTrue(request.getHeaders().containsKey("User-Agent"));
        assertTrue(request.getHeaders().containsKey("Authorization"));
        assertEquals("retroachievements4j/v0.0.0", request.getHeaders().get("User-Agent"));
        assertEquals("Bearer secret_bearer", request.getHeaders().get("Authorization"));
        assertEquals(7, request.getQueryParameters().size());
        assertTrue(request.getQueryParameters().containsKey("g"));
        assertTrue(request.getQueryParameters().containsKey("i"));
        assertTrue(request.getQueryParameters().containsKey("p"));
        assertTrue(request.getQueryParameters().containsKey("r"));
        assertTrue(request.getQueryParameters().containsKey("t"));
        assertTrue(request.getQueryParameters().containsKey("u"));
        assertTrue(request.getQueryParameters().containsKey("y"));
        assertEquals(1, request.getFormParts().size());
        assertEquals("good", request.getFormParts().get("state"));
        assertEquals("32", request.getQueryParameters().get("g"));
        assertEquals("133,42", request.getQueryParameters().get("i"));
        assertEquals("secret_password", request.getQueryParameters().get("p"));
        assertEquals("login2", request.getQueryParameters().get("r"));
        assertEquals("secret_token1", request.getQueryParameters().get("t"));
        assertEquals("myUsername", request.getQueryParameters().get("u"));
        assertEquals("secret_token2", request.getQueryParameters().get("y"));
        assertDoesNotThrow(() -> {
            ClassicHttpRequest r = request.build();
            assertEquals("http", r.getUri().getScheme());
            assertEquals("localhost", r.getUri().getHost());
            assertEquals("/api/v1/some_resource", r.getUri().getPath());
        });
    }
}
