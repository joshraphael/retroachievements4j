package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.models.connect.Login;
import com.joshraphael.retroachievements4j.models.connect.Ping;
import com.joshraphael.retroachievements4j.models.connect.StartSession;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectTest {
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
        assertDoesNotThrow(() -> server.shutdown());
    }

    @Test
    void testLogin() {
        assertDoesNotThrow(() -> {
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
            CloseableHttpClient httpClient = HttpClients.createDefault();
            RetroAchievementsClient c = new RetroAchievementsClient(httpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0");

            ApiResponse<Login> login = c.Login("username", "password");
            RecordedRequest request = server.takeRequest();

            // Validate request
            assertEquals("POST", request.getMethod());
            assertEquals("/dorequest.php?p=password&r=login2&u=username", request.getPath());

            // Validate response
            assertEquals(200, login.statusCode());
            assertTrue(login.resp().Success());
            assertEquals("OldSchoolRunescape", login.resp().User());
            assertEquals("4AotgGxjIH5iT1gz", login.resp().Token());
            assertEquals(1, login.resp().Score());
            assertEquals(0, login.resp().SoftcoreScore());
            assertEquals(0, login.resp().Messages());
            assertEquals(1, login.resp().Permissions());
            assertEquals("Registered", login.resp().AccountType());
            assertNull(login.resp().Error());
            assertNull(login.resp().Code());
            assertEquals(0, login.resp().Status());
        });
    }

    @Test
    void testStartSession() {
        assertDoesNotThrow(() -> {
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
            CloseableHttpClient httpClient = HttpClients.createDefault();
            RetroAchievementsClient c = new RetroAchievementsClient(httpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0");

            ApiResponse<StartSession> session = c.StartSession("username", "4AotgGxjIH5iT1gz", 23, "targetUser");
            RecordedRequest request = server.takeRequest();

            // Validate request
            assertEquals("POST", request.getMethod());
            assertEquals("/dorequest.php?r=startsession&t=4AotgGxjIH5iT1gz&u=username&g=23&k=targetUser", request.getPath());

            // Validate response
            assertEquals(200, session.statusCode());
            assertTrue(session.resp().Success());
            assertFalse(session.resp().HardcoreUnlocks().isEmpty());
            assertNull(session.resp().Unlocks());
            assertEquals(1, session.resp().HardcoreUnlocks().size());
            assertEquals(141, session.resp().HardcoreUnlocks().getFirst().ID());
            assertEquals(1591132445, session.resp().HardcoreUnlocks().getFirst().When());
            assertEquals(1704076711, session.resp().ServerNow());
            assertNull(session.resp().Error());
            assertNull(session.resp().Code());
            assertEquals(0, session.resp().Status());
        });
    }

    @Test
    void testPing() {
        assertDoesNotThrow(() -> {
            server.enqueue(new MockResponse().setBody("""
            {
                "Success": true
            }
            """));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            RetroAchievementsClient c = new RetroAchievementsClient(httpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0");

            ApiResponse<Ping> ping = c.Ping("username", "4AotgGxjIH5iT1gz", 23, "targetUser", "Player is doing something");
            RecordedRequest request = server.takeRequest();

            // Validate request
            assertEquals("POST", request.getMethod());
            assertEquals("/dorequest.php?r=ping&t=4AotgGxjIH5iT1gz&u=username&g=23&k=targetUser", request.getPath());
            assertTrue(new String(request.getBody().readByteArray()).contains("Player is doing something"));

            // Validate response
            assertEquals(200, ping.statusCode());
            assertTrue(ping.resp().Success());
        });
    }
}
