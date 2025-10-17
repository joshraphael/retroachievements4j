package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.models.connect.Login;
import com.joshraphael.retroachievements4j.models.connect.StartSession;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.http.HttpClient;

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

            ApiResponse<Login> login = c.Login("username", "password");
            RecordedRequest request = server.takeRequest();

            // Validate request
            Assertions.assertEquals("POST", request.getMethod());
            Assertions.assertEquals("/dorequest.php?p=password&r=login2&u=username", request.getPath());

            // Validate response
            Assertions.assertEquals(200, login.statusCode());
            Assertions.assertTrue(login.resp().Success());
            Assertions.assertEquals("OldSchoolRunescape", login.resp().User());
            Assertions.assertEquals("4AotgGxjIH5iT1gz", login.resp().Token());
            Assertions.assertEquals(1, login.resp().Score());
            Assertions.assertEquals(0, login.resp().SoftcoreScore());
            Assertions.assertEquals(0, login.resp().Messages());
            Assertions.assertEquals(1, login.resp().Permissions());
            Assertions.assertEquals("Registered", login.resp().AccountType());
            Assertions.assertNull(login.resp().Error());
            Assertions.assertNull(login.resp().Code());
            Assertions.assertEquals(0, login.resp().Status());
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

            ApiResponse<StartSession> session = c.StartSession("username", "4AotgGxjIH5iT1gz", 23);
            RecordedRequest request = server.takeRequest();

            // Validate request
            Assertions.assertEquals("POST", request.getMethod());
            Assertions.assertEquals("/dorequest.php?r=startsession&t=4AotgGxjIH5iT1gz&u=username&g=23", request.getPath());

            // Validate response
            Assertions.assertEquals(200, session.statusCode());
            Assertions.assertTrue(session.resp().Success());
            Assertions.assertFalse(session.resp().HardcoreUnlocks().isEmpty());
            Assertions.assertNull(session.resp().Unlocks());
            Assertions.assertEquals(1, session.resp().HardcoreUnlocks().size());
            Assertions.assertEquals(141, session.resp().HardcoreUnlocks().getFirst().ID());
            Assertions.assertEquals(1591132445, session.resp().HardcoreUnlocks().getFirst().When());
            Assertions.assertEquals(1704076711, session.resp().ServerNow());
            Assertions.assertNull(session.resp().Error());
            Assertions.assertNull(session.resp().Code());
            Assertions.assertEquals(0, session.resp().Status());
        });
    }
}
