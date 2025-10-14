package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.BadHttpResponseException;
import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.game.GetGame;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientTest {
    public static MockWebServer server;
    public static RecordedRequest recordedRequest;

    @BeforeAll
    static void setUp() throws IOException, InterruptedException {
        server = new MockWebServer();
        server.start(0);
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
        HttpClient mockHttpClient = EasyMock.createMock(HttpClient.class);
        EasyMock.expect(mockHttpClient.send(EasyMock.anyObject(HttpRequest.class), EasyMock.eq(HttpResponse.BodyHandlers.ofString()))).andThrow(new IOException("Simulated network error"));
        EasyMock.replay(mockHttpClient);
        Client c = new Client(mockHttpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
        Request r = c.newRequestBuilder();
        assertThrows(IOException.class, () -> {
            c.Do(r, String.class);
        });
        EasyMock.verify(mockHttpClient);
    }

    @Test
    void testDoInterruptedException() throws IOException, InterruptedException {
        HttpClient mockHttpClient = EasyMock.createMock(HttpClient.class);
        EasyMock.expect(mockHttpClient.send(EasyMock.anyObject(HttpRequest.class), EasyMock.eq(HttpResponse.BodyHandlers.ofString()))).andThrow(new InterruptedException("Client Shut down"));
        EasyMock.replay(mockHttpClient);
        Client c = new Client(mockHttpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
        Request r = c.newRequestBuilder();
        assertThrows(InterruptedException.class, () -> {
            c.Do(r, String.class);
        });
        EasyMock.verify(mockHttpClient);
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
