package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.models.game.GetGame;
import com.joshraphael.retroachievements4j.models.game.GetGameExtended;
import com.joshraphael.retroachievements4j.models.game.GetGameExtendedAchievement;
import com.joshraphael.retroachievements4j.models.game.GetGameHashes;
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
        assertDoesNotThrow(server::shutdown);
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
            assertNull(getGame.resp().Message());
            assertNull(getGame.resp().Errors());
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

            // Validate response
            assertEquals(200, getGameExtended.statusCode());
            assertEquals(1, getGameExtended.resp().ID());
            assertEquals("Sonic the Hedgehog", getGameExtended.resp().Title());
            assertEquals(1, getGameExtended.resp().ConsoleID());
            assertEquals(112, getGameExtended.resp().ForumTopicID());
            assertNull(getGameExtended.resp().Flags());
            assertEquals("/Images/067895.png", getGameExtended.resp().ImageIcon());
            assertEquals("/Images/054993.png", getGameExtended.resp().ImageTitle());
            assertEquals("/Images/000010.png", getGameExtended.resp().ImageIngame());
            assertEquals("/Images/051872.png", getGameExtended.resp().ImageBoxArt());
            assertEquals("", getGameExtended.resp().Publisher());
            assertEquals("", getGameExtended.resp().Developer());
            assertEquals("", getGameExtended.resp().Genre());
            assertEquals("1992-06-02", getGameExtended.resp().Released());
            assertEquals("day", getGameExtended.resp().ReleasedAtGranularity());
            assertFalse(getGameExtended.resp().IsFinal());
            assertEquals("cce60593880d25c97797446ed33eaffb", getGameExtended.resp().RichPresencePatch());
            assertNull(getGameExtended.resp().GuideURL());
            assertEquals("2023-12-27T13:51:14.000000Z", getGameExtended.resp().Updated());
            assertEquals("Mega Drive", getGameExtended.resp().ConsoleName());
            assertNull(getGameExtended.resp().ParentGameID());
            assertEquals(27080, getGameExtended.resp().NumDistinctPlayers());
            assertEquals(23, getGameExtended.resp().NumAchievements());
            assertNotNull(getGameExtended.resp().Achievements());
            assertEquals(1, getGameExtended.resp().Achievements().size());
            assertNotNull(getGameExtended.resp().Achievements().get("9"));
            GetGameExtendedAchievement ach = getGameExtended.resp().Achievements().get("9");
            assertEquals(9, ach.ID());
            assertEquals(24273, ach.NumAwarded());
            assertEquals(10831, ach.NumAwardedHardcore());
            assertEquals("That Was Easy", ach.Title());
            assertEquals("Complete the first act in Green Hill Zone", ach.Description());
            assertEquals(3, ach.Points());
            assertEquals(3, ach.TrueRatio());
            assertEquals("Scott", ach.Author());
            assertEquals("00003EMFWR7XB8SDPEHB3K56ZQ", ach.AuthorULID());
            assertEquals("2023-08-08 00:36:59", ach.DateModified());
            assertEquals("2012-11-02 00:03:12", ach.DateCreated());
            assertEquals("250336", ach.BadgeName());
            assertEquals(1, ach.DisplayOrder());
            assertEquals("22c9d5e2cd7571df18a1a1b43dfe1fea", ach.MemAddr());
            assertEquals("progression", ach.Type());
            assertEquals(0, getGameExtended.resp().Claims().size());
            assertEquals(27080, getGameExtended.resp().NumDistinctPlayers());
            assertEquals(27080, getGameExtended.resp().NumDistinctPlayersHardcore());
            assertNull(getGameExtended.resp().Message());
            assertNull(getGameExtended.resp().Errors());
        });
    }

    @Test
    void testGetGameHashes() {
        assertDoesNotThrow(() -> {
            server.enqueue(new MockResponse().setBody("""
            {
                "Results": [
                    {
                        "MD5": "1b1d9ac862c387367e904036114c4825",
                        "Name": "Sonic The Hedgehog (USA, Europe) (Ru) (NewGame).md",
                        "Labels": ["nointro", "rapatches"],
                        "PatchUrl": "https://github.com/RetroAchievements/RAPatches/raw/main/MD/Translation/Russian/1-Sonic1-Russian.zip"
                    },
                    {
                        "MD5": "1bc674be034e43c96b86487ac69d9293",
                        "Name": "Sonic The Hedgehog (USA, Europe).md",
                        "Labels": ["nointro"],
                        "PatchUrl": null
                    }
                ]
            }
            """));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            RetroAchievementsClient c = new RetroAchievementsClient(httpClient, "http://" + server.getHostName() + ":" + server.getPort(), "retroachievements4j/v0.0.0");
            ApiResponse<GetGameHashes> getGameHashes = c.GetGameHashes("secret_token", 123);
            RecordedRequest request = server.takeRequest();
            // Validate request
            assertEquals("GET", request.getMethod());
            assertEquals("/API/API_GetGameHashes.php?y=secret_token&i=123", request.getPath());

            // Validate response
            assertEquals(200, getGameHashes.statusCode());
            assertEquals(2, getGameHashes.resp().Results().length);
            assertEquals("1b1d9ac862c387367e904036114c4825", getGameHashes.resp().Results()[0].MD5());
            assertEquals("Sonic The Hedgehog (USA, Europe) (Ru) (NewGame).md", getGameHashes.resp().Results()[0].Name());
            assertEquals("https://github.com/RetroAchievements/RAPatches/raw/main/MD/Translation/Russian/1-Sonic1-Russian.zip", getGameHashes.resp().Results()[0].PatchUrl());
            assertEquals(2, getGameHashes.resp().Results()[0].Labels().length);
            assertEquals("nointro", getGameHashes.resp().Results()[0].Labels()[0]);
            assertEquals("rapatches", getGameHashes.resp().Results()[0].Labels()[1]);
            assertEquals("1bc674be034e43c96b86487ac69d9293", getGameHashes.resp().Results()[1].MD5());
            assertEquals("Sonic The Hedgehog (USA, Europe).md", getGameHashes.resp().Results()[1].Name());
            assertNull(getGameHashes.resp().Results()[1].PatchUrl());
            assertEquals(1, getGameHashes.resp().Results()[1].Labels().length);
            assertEquals("nointro", getGameHashes.resp().Results()[1].Labels()[0]);
            assertNull(getGameHashes.resp().Message());
            assertNull(getGameHashes.resp().Errors());
        });
    }
}
