package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.game.GetGame;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

class ClientTest {
    public static MockWebServer server;

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> {
            server = new MockWebServer();
            server.start(0);
        });
    }

    @AfterEach
    void tearDown() {
        assertDoesNotThrow(() -> {
            server.shutdown();
        });
    }
    @Test
    void testNewRequestBuilder() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Client c = new Client(httpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
        Request r = c.newRequestBuilder();
        assertEquals("http://localhost:" + server.getPort(), r.getHost());
        assertEquals("retroachievements4j/v0.0.0", r.getHeaders().get("User-Agent"));
    }

    @Test
    void testDoURISyntaxException() {
        assertThrows(URISyntaxException.class, () -> {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            Client c = new Client(httpClient, "??>3@>@4>2:@#", "retroachievements4j/v0.0.0", "secret_token");
            Request r = c.newRequestBuilder();
            c.Do(r, String.class);
        });
    }

    @Test
    void testDoIOException() {
        assertThrows(IOException.class, () -> {
            CloseableHttpClient mockHttpClient = EasyMock.createMock(CloseableHttpClient.class);
            EasyMock.expect(mockHttpClient.execute(EasyMock.anyObject(ClassicHttpRequest.class), EasyMock.anyObject(HttpClientResponseHandler.class))).andThrow(new IOException("Simulated network error"));
            EasyMock.replay(mockHttpClient);
            Client c = new Client(mockHttpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
            Request r = c.newRequestBuilder();
            c.Do(r, String.class);
            EasyMock.verify(mockHttpClient);
        });
    }

    @Test
    void testDo() {
        assertDoesNotThrow(() -> {
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
            CloseableHttpClient httpClient = HttpClients.createDefault();
            Client c = new Client(httpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");
            Request r = c.newRequestBuilder()
                    .methodGET()
                    .path("/api/v1/some_resource")
                    .Y(c.getWebToken());
            ApiResponse<GetGame> g = c.Do(r, GetGame.class);
            RecordedRequest request = server.takeRequest();
            // Validate request
            assertEquals("GET", request.getMethod());
            assertEquals("/api/v1/some_resource?y=secret_token", request.getPath());

            // Validate response
            assertEquals(200, g.statusCode());
            assertEquals("Sonic the Hedgehog", g.resp().Title());
            assertEquals("Sonic the Hedgehog", g.resp().GameTitle());
            assertEquals(1, g.resp().ConsoleID());
            assertEquals("Mega Drive", g.resp().ConsoleName());
            assertEquals("Mega Drive", g.resp().Console());
            assertEquals(112, g.resp().ForumTopicID());
            assertEquals(0, g.resp().Flags());
            assertEquals("/Images/067895.png", g.resp().GameIcon());
            assertEquals("/Images/067895.png", g.resp().ImageIcon());
            assertEquals("/Images/054993.png", g.resp().ImageTitle());
            assertEquals("/Images/000010.png", g.resp().ImageIngame());
            assertEquals("/Images/051872.png", g.resp().ImageBoxArt());
            assertEquals("", g.resp().Publisher());
            assertEquals("", g.resp().Developer());
            assertEquals("", g.resp().Genre());
            assertEquals("1992-06-02 00:00:00", g.resp().Released());
            assertEquals("day", g.resp().ReleasedAtGranularity());
        });
    }
}
