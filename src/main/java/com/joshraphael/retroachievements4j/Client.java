package com.joshraphael.retroachievements4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.connect.Login;
import com.joshraphael.retroachievements4j.models.connect.Ping;
import com.joshraphael.retroachievements4j.models.connect.StartSession;
import com.joshraphael.retroachievements4j.models.game.GetGame;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class Client implements RetroAchievements {
    private final CloseableHttpClient client;
    private final String host;
    private final String userAgent;
    private final String webToken;
    private final Game game;
    private final Connect connect;

    public Client(CloseableHttpClient client, String host, String userAgent, String webToken) {
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

    public <T> ApiResponse<T> Do(Request req, Class<T> t) throws IOException, URISyntaxException {
        HttpClientResponseHandler<ApiResponse<T>> responseHandler = response -> {
            String body = new String(response.getEntity().getContent().readAllBytes());
            ObjectMapper mapper = new ObjectMapper();
            T respType = mapper.readValue(body, t);
            return new ApiResponse<>(response.getCode(), respType);
        };
        return this.client.execute(req.build(), responseHandler);
    }

    public ApiResponse<GetGame> GetGame(int gameID) throws IOException, URISyntaxException {
        return this.game.GetGame(gameID);
    }

    public ApiResponse<Login> Login(String username, String password) throws IOException, URISyntaxException {
        return this.connect.Login(username, password);
    }

    public ApiResponse<StartSession> StartSession(String username, String token, int gameID) throws IOException, URISyntaxException {
        return this.connect.StartSession(username, token, gameID);
    }

    public ApiResponse<Ping> Ping(String username, String token, int gameID, String richPresence) throws IOException, URISyntaxException {
        return this.connect.Ping(username, token, gameID, richPresence);
    }
}
