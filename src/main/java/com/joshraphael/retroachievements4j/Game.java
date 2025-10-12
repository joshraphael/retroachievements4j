package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.BadHttpResponseException;
import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.game.GetGame;

import java.io.IOException;
import java.net.URISyntaxException;

class Game {
    private final Client c;
    public Game(Client c) {
        this.c = c;
    }
    public GetGame GetGame(int gameID) throws IOException, URISyntaxException, InterruptedException, BadHttpResponseException {
        Request r = this.c.newRequestBuilder().path("/API/API_GetGame.php");
        return this.c.Do(r, GetGame.class);
    }
}
