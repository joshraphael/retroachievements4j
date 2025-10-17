package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.game.GetGame;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class ClientTest {
    public static MockWebServer server;

    @BeforeEach
    void setUp() {
        Assertions.assertDoesNotThrow(() -> {
            server = new MockWebServer();
            server.start(0);
        });
    }

    @AfterEach
    void tearDown() {
        Assertions.assertDoesNotThrow(() -> {
            server.shutdown();
        });
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
        Assertions.assertThrows(URISyntaxException.class, () -> {
            HttpClient http = HttpClient.newHttpClient();
            Client c = new Client(http, "??>3@>@4>2:@#", "retroachievements4j/v0.0.0", "secret_token");
            Request r = c.newRequestBuilder();
            c.Do(r, String.class);
        });
    }

    @Test
    void testDoIOException() {
        Assertions.assertThrows(IOException.class, () -> {
            HttpClient mockHttpClient = EasyMock.createMock(HttpClient.class);
            EasyMock.expect(mockHttpClient.send(EasyMock.anyObject(HttpRequest.class), EasyMock.eq(HttpResponse.BodyHandlers.ofString()))).andThrow(new IOException("Simulated network error"));
            EasyMock.replay(mockHttpClient);
            Client c = new Client(mockHttpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
            Request r = c.newRequestBuilder();
            c.Do(r, String.class);
            EasyMock.verify(mockHttpClient);
        });
    }

    @Test
    void testDoInterruptedException() {
        Assertions.assertThrows(InterruptedException.class, () -> {
            HttpClient mockHttpClient = EasyMock.createMock(HttpClient.class);
            EasyMock.expect(mockHttpClient.send(EasyMock.anyObject(HttpRequest.class), EasyMock.eq(HttpResponse.BodyHandlers.ofString()))).andThrow(new InterruptedException("Client Shut down"));
            EasyMock.replay(mockHttpClient);
            Client c = new Client(mockHttpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
            Request r = c.newRequestBuilder();
            c.Do(r, String.class);
            EasyMock.verify(mockHttpClient);
        });
    }

    @Test
    void testDo() {
        Assertions.assertDoesNotThrow(() -> {
            server.enqueue(new MockResponse().setBody("""
            {
                "Title": "Sonic the Hedgehog",
                "GameTitle": "Sonic the Hedgehog",
                "ConsoleID": 1,
                "ConsoleName": "Mega Drive",
                "Console": "Mega Drive",
                "ForumTopicID": 112,
                "Flags": 0,
                "GameIcon": "/Images/067895.png",
                "ImageIcon": "/Images/067895.png",
                "ImageTitle": "/Images/054993.png",
                "ImageIngame": "/Images/000010.png",
                "ImageBoxArt": "/Images/051872.png",
                "Publisher": "",
                "Developer": "",
                "Genre": "",
                "Released": "1992-06-02 00:00:00",
                "ReleasedAtGranularity": "day"
            }
            """));
            HttpClient http = HttpClient.newHttpClient();
            Client c = new Client(http, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
            Request r = c.newRequestBuilder()
                    .methodGET()
                    .path("/api/v1/some_resource")
                    .Y(c.getWebToken());
            ApiResponse<GetGame> g = c.Do(r, GetGame.class);
            RecordedRequest request = server.takeRequest();
            // Validate request
            Assertions.assertEquals("GET", request.getMethod());
            Assertions.assertEquals("/api/v1/some_resource?y=secret_token", request.getPath());

            // Validate response
            Assertions.assertEquals(200, g.statusCode());
            Assertions.assertEquals("Sonic the Hedgehog", g.resp().Title());
            Assertions.assertEquals("Sonic the Hedgehog", g.resp().GameTitle());
            Assertions.assertEquals(1, g.resp().ConsoleID());
            Assertions.assertEquals("Mega Drive", g.resp().ConsoleName());
            Assertions.assertEquals("Mega Drive", g.resp().Console());
            Assertions.assertEquals(112, g.resp().ForumTopicID());
            Assertions.assertEquals(0, g.resp().Flags());
            Assertions.assertEquals("/Images/067895.png", g.resp().GameIcon());
            Assertions.assertEquals("/Images/067895.png", g.resp().ImageIcon());
            Assertions.assertEquals("/Images/054993.png", g.resp().ImageTitle());
            Assertions.assertEquals("/Images/000010.png", g.resp().ImageIngame());
            Assertions.assertEquals("/Images/051872.png", g.resp().ImageBoxArt());
            Assertions.assertEquals("", g.resp().Publisher());
            Assertions.assertEquals("", g.resp().Developer());
            Assertions.assertEquals("", g.resp().Genre());
            Assertions.assertEquals("1992-06-02 00:00:00", g.resp().Released());
            Assertions.assertEquals("day", g.resp().ReleasedAtGranularity());
        });
    }
}
