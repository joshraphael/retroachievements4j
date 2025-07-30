package com.joshraphael.retroachievements4j.http;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {
    @Test
    void testBuilder() {
        Request request = new Request.Builder().host("http://localhost").path("/api/v1/some_resource").build();
        assertEquals("http://localhost", request.getHost());
        assertEquals("/api/v1/some_resource", request.getPath());
    }
}
