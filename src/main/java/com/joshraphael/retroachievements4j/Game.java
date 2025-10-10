package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.game.GetGame;

class Game {
    private final Client c;
    public Game(Client c) {
        this.c = c;
    }
    public GetGame GetGame(int gameID) {
        Request r = this.c.newRequestBuilder().path("/API/API_GetGame.php");
        return this.c.Do(r, GetGame.class);
    }
}
