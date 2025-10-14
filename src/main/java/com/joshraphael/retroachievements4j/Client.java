package com.joshraphael.retroachievements4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshraphael.retroachievements4j.http.BadHttpResponseException;
import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.game.GetGame;

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

    public Client(HttpClient client, String host, String userAgent, String webToken) {
        this.client = client;
        this.host = host;
        this.userAgent = userAgent;
        this.webToken = webToken;
        this.game = new Game(this);
    }

    public Request newRequestBuilder() {
        return new Request()
                .host(this.host)
                .userAgent(this.userAgent);
    }

    public <T> T Do(Request r, Class<T> t) throws IOException, URISyntaxException, InterruptedException, BadHttpResponseException {
        HttpResponse<String> resp = this.client.send(r.build(), HttpResponse.BodyHandlers.ofString());
        String body = resp.body();
        if(resp.statusCode() != 200) {
            throw new BadHttpResponseException(resp.statusCode(), body);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, t);
    }

    public GetGame GetGame(int gameID) throws IOException, URISyntaxException, InterruptedException, BadHttpResponseException {
        return this.game.GetGame(gameID);
    }
}
