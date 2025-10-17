package com.joshraphael.retroachievements4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.connect.Login;
import com.joshraphael.retroachievements4j.models.connect.StartSession;
import com.joshraphael.retroachievements4j.models.game.GetGame;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class Client implements RetroAchievements {
    private final HttpClient client;
    private final String host;
    private final String userAgent;
    private final String webToken;
    private final Game game;
    private final Connect connect;

    public Client(HttpClient client, String host, String userAgent, String webToken) {
        this.client = client;
        this.host = host;
        this.userAgent = userAgent;
        this.webToken = webToken;
        this.game = new Game(this);
        this.connect = new Connect(this);
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public String getWebToken() {
        return this.webToken;
    }

    public Request newRequestBuilder() {
        return new Request()
                .host(this.host)
                .userAgent(this.userAgent);
    }

    public <T> ApiResponse<T> Do(Request req, Class<T> t) throws IOException, URISyntaxException, InterruptedException {
        HttpResponse<String> resp = this.client.send(req.build(), HttpResponse.BodyHandlers.ofString());
        String body = resp.body();
        ObjectMapper mapper = new ObjectMapper();
        T respType = mapper.readValue(body, t);
        return new ApiResponse<>(resp.statusCode(), respType);
    }

    public ApiResponse<GetGame> GetGame(int gameID) throws IOException, URISyntaxException, InterruptedException {
        return this.game.GetGame(gameID);
    }

    public ApiResponse<Login> Login(String username, String password) throws IOException, URISyntaxException, InterruptedException {
        return this.connect.Login(username, password);
    }

    public ApiResponse<StartSession> StartSession(String username, String token, int gameID) throws IOException, URISyntaxException, InterruptedException {
        return this.connect.StartSession(username, token, gameID);
    }
}
