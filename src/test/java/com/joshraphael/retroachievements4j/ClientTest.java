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
        Assertions.assertThrows(URISyntaxException.class, () -> {
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
        Assertions.assertThrows(IOException.class, () -> {
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
        Assertions.assertThrows(InterruptedException.class, () -> {
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
        Assertions.assertThrows(BadHttpResponseException.class, () -> {
            c.Do(r, String.class);
        });
    }

    @Test
    void testDo() throws IOException, URISyntaxException, InterruptedException {
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
        Request r = c.newRequestBuilder();
        GetGame g = c.Do(r, GetGame.class);
        Assertions.assertEquals("Sonic the Hedgehog", g.Title);
        Assertions.assertEquals("Sonic the Hedgehog", g.GameTitle);
        Assertions.assertEquals(1, g.ConsoleID);
        Assertions.assertEquals("Mega Drive", g.ConsoleName);
        Assertions.assertEquals("Mega Drive", g.Console);
        Assertions.assertEquals(112, g.ForumTopicID);
        Assertions.assertEquals(0, g.Flags);
        Assertions.assertEquals("/Images/067895.png", g.GameIcon);
        Assertions.assertEquals("/Images/067895.png", g.ImageIcon);
        Assertions.assertEquals("/Images/054993.png", g.ImageTitle);
        Assertions.assertEquals("/Images/000010.png", g.ImageIngame);
        Assertions.assertEquals("/Images/051872.png", g.ImageBoxArt);
        Assertions.assertEquals("", g.Publisher);
        Assertions.assertEquals("", g.Developer);
        Assertions.assertEquals("", g.Genre);
        Assertions.assertEquals("1992-06-02 00:00:00", g.Released);
        Assertions.assertEquals("day", g.ReleasedAtGranularity);
    }
}
