package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.BadHttpResponseException;
import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.game.GetGame;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ClientTest {
    public static MockWebServer server;
    public static RecordedRequest recordedRequest;
    public static HttpClient mockHttpClient;

    @BeforeAll
    static void setUp() throws IOException, InterruptedException {
        server = new MockWebServer();
        server.start(0);
        mockHttpClient = Mockito.mock(HttpClient.class);
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }
    @Test
    void testNewRequestBuilder() {
        HttpClient http = HttpClient.newHttpClient();
        Client c = new Client(http, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
        Request r = c.newRequestBuilder();
        Assertions.assertEquals("http://localhost:" + server.getPort(), r.getHost());
        Assertions.assertEquals("retroachievements4j/v0.0.0", r.getHeaders().get("User-Agent"));
    }

    @Test
    void testDoURISyntaxException() {
        HttpClient http = HttpClient.newHttpClient();
        Client c = new Client(http, "??>3@>@4>2:@#", "retroachievements4j/v0.0.0", "secret_token");
        Request r = c.newRequestBuilder();
        assertThrows(URISyntaxException.class, () -> {
            c.Do(r, String.class);
        });
    }

    @Test
    void testDoIOException() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()))).thenThrow(new IOException("Simulated network error")); // Or any other desired exception
        Client c = new Client(mockHttpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
        Request r = c.newRequestBuilder();
        assertThrows(IOException.class, () -> {
            c.Do(r, String.class);
        });
    }

    @Test
    void testDoInterruptedException() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()))).thenThrow(new InterruptedException("Client Shut down")); // Or any other desired exception
        Client c = new Client(mockHttpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
        Request r = c.newRequestBuilder();
        assertThrows(InterruptedException.class, () -> {
            c.Do(r, String.class);
        });
    }

    @Test
    void testDoBadHttpResponseException() throws BadHttpResponseException {
        server.enqueue(new MockResponse().setResponseCode(404));
        HttpClient http = HttpClient.newHttpClient();
        Client c = new Client(http, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
        Request r = c.newRequestBuilder();
        assertThrows(BadHttpResponseException.class, () -> {
            c.Do(r, String.class);
        });
    }

    @Test
    void testDo() throws IOException, URISyntaxException, InterruptedException {
        server.enqueue(new MockResponse().setBody("{\"Title\": \"some_game\"}"));
        HttpClient http = HttpClient.newHttpClient();
        Client c = new Client(http, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
        Request r = c.newRequestBuilder();
        c.Do(r, GetGame.class);
    }
}
