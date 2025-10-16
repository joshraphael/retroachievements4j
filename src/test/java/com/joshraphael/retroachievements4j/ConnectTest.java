package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.connect.Login;
import com.joshraphael.retroachievements4j.models.connect.StartSession;
import com.joshraphael.retroachievements4j.models.game.GetGame;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConnectTest {
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
    void testLogin() {
        Assertions.assertDoesNotThrow(() -> {
            server.enqueue(new MockResponse().setBody("""
            {
                "Success": true,
                "User": "OldSchoolRunescape",
                "Token": "4AotgGxjIH5iT1gz",
                "Score": 1,
                "SoftcoreScore": 0,
                "Messages": 0,
                "Permissions": 1,
                "AccountType": "Registered"
            }
            """));
            HttpClient http = HttpClient.newHttpClient();
            Client c = new Client(http, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");

            Login login = c.Login("username", "password");
            RecordedRequest request = server.takeRequest();

            // Validate request
            Assertions.assertEquals("POST", request.getMethod());
            Assertions.assertEquals("/dorequest.php?p=password&r=login2&u=username", request.getPath());

            // Validate response
            Assertions.assertTrue(login.Success());
            Assertions.assertEquals("OldSchoolRunescape", login.User());
            Assertions.assertEquals("4AotgGxjIH5iT1gz", login.Token());
            Assertions.assertEquals(1, login.Score());
            Assertions.assertEquals(0, login.SoftcoreScore());
            Assertions.assertEquals(0, login.Messages());
            Assertions.assertEquals(1, login.Permissions());
            Assertions.assertEquals("Registered", login.AccountType());
            Assertions.assertNull(login.Error());
            Assertions.assertNull(login.Code());
            Assertions.assertEquals(0, login.Status());
        });
    }

    @Test
    void testStartSession() {
        Assertions.assertDoesNotThrow(() -> {
            server.enqueue(new MockResponse().setBody("""
            {
                "Success": true,
                "HardcoreUnlocks": [
                    {
                        "ID": 141,
                        "When": 1591132445
                    }
                ],
                "ServerNow": 1704076711
            }
            """));
            HttpClient http = HttpClient.newHttpClient();
            Client c = new Client(http, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0", "secret_token");

            StartSession session = c.StartSession("username", "4AotgGxjIH5iT1gz", 23);
            RecordedRequest request = server.takeRequest();

            // Validate request
            Assertions.assertEquals("POST", request.getMethod());
            Assertions.assertEquals("/dorequest.php?r=startsession&t=4AotgGxjIH5iT1gz&u=username&g=23", request.getPath());

            // Validate response
            Assertions.assertTrue(session.Success());
            Assertions.assertFalse(session.HardcoreUnlocks().isEmpty());
            Assertions.assertNull(session.Unlocks());
            Assertions.assertEquals(1, session.HardcoreUnlocks().size());
            Assertions.assertEquals(141, session.HardcoreUnlocks().getFirst().ID());
            Assertions.assertEquals(1591132445, session.HardcoreUnlocks().getFirst().When());
            Assertions.assertEquals(1704076711, session.ServerNow());
            Assertions.assertNull(session.Error());
            Assertions.assertNull(session.Code());
            Assertions.assertEquals(0, session.Status());
        });
    }
}
