package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.models.game.GetGame;
import com.joshraphael.retroachievements4j.models.game.GetGameExtended;
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

public class GameTest {
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
    void testGetGame() {
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
            RetroAchievementsClient c = new RetroAchievementsClient(httpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0");
            ApiResponse<GetGame> getGame = c.GetGame("secret_token", 123);
            RecordedRequest request = server.takeRequest();
            // Validate request
            assertEquals("GET", request.getMethod());
            assertEquals("/API/API_GetGame.php?y=secret_token&i=123", request.getPath());

            // Validate response
            assertEquals(200, getGame.statusCode());
            assertEquals("Sonic the Hedgehog", getGame.resp().Title());
            assertEquals("Sonic the Hedgehog", getGame.resp().GameTitle());
            assertEquals(1, getGame.resp().ConsoleID());
            assertEquals("Mega Drive", getGame.resp().ConsoleName());
            assertEquals("Mega Drive", getGame.resp().Console());
            assertEquals(112, getGame.resp().ForumTopicID());
            assertEquals(0, getGame.resp().Flags());
            assertEquals("/Images/067895.png", getGame.resp().GameIcon());
            assertEquals("/Images/067895.png", getGame.resp().ImageIcon());
            assertEquals("/Images/054993.png", getGame.resp().ImageTitle());
            assertEquals("/Images/000010.png", getGame.resp().ImageIngame());
            assertEquals("/Images/051872.png", getGame.resp().ImageBoxArt());
            assertEquals("", getGame.resp().Publisher());
            assertEquals("", getGame.resp().Developer());
            assertEquals("", getGame.resp().Genre());
            assertEquals("1992-06-02 00:00:00", getGame.resp().Released());
            assertEquals("day", getGame.resp().ReleasedAtGranularity());
        });
    }

    @Test
    void testGetGameExtended() {
        assertDoesNotThrow(() -> {
            server.enqueue(new MockResponse().setBody("""
            {
                "ID": 1,
                "Title": "Sonic the Hedgehog",
                "ConsoleID": 1,
                "ForumTopicID": 112,
                "Flags": null,
                "ImageIcon": "/Images/067895.png",
                "ImageTitle": "/Images/054993.png",
                "ImageIngame": "/Images/000010.png",
                "ImageBoxArt": "/Images/051872.png",
                "Publisher": "",
                "Developer": "",
                "Genre": "",
                "Released": "1992-06-02",
                "ReleasedAtGranularity": "day",
                "IsFinal": false,
                "RichPresencePatch": "cce60593880d25c97797446ed33eaffb",
                "GuideURL": null,
                "Updated": "2023-12-27T13:51:14.000000Z",
                "ConsoleName": "Mega Drive",
                "ParentGameID": null,
                "NumDistinctPlayers": 27080,
                "NumAchievements": 23,
                "Achievements": {
                  "9": {
                    "ID": 9,
                    "NumAwarded": 24273,
                    "NumAwardedHardcore": 10831,
                    "Title": "That Was Easy",
                    "Description": "Complete the first act in Green Hill Zone",
                    "Points": 3,
                    "TrueRatio": 3,
                    "Author": "Scott",
                    "AuthorULID": "00003EMFWR7XB8SDPEHB3K56ZQ",
                    "DateModified": "2023-08-08 00:36:59",
                    "DateCreated": "2012-11-02 00:03:12",
                    "BadgeName": "250336",
                    "DisplayOrder": 1,
                    "MemAddr": "22c9d5e2cd7571df18a1a1b43dfe1fea",
                    "type": "progression"
                  }
                },
                "Claims": [],
                "NumDistinctPlayersCasual": 27080,
                "NumDistinctPlayersHardcore": 27080
            }
            """));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            RetroAchievementsClient c = new RetroAchievementsClient(httpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0");
            ApiResponse<GetGameExtended> getGameExtended = c.GetGameExtended("secret_token", 123, true);
            RecordedRequest request = server.takeRequest();
            // Validate request
            assertEquals("GET", request.getMethod());
            assertEquals("/API/API_GetGameExtended.php?f=5&y=secret_token&i=123", request.getPath());
        });
    }
}
