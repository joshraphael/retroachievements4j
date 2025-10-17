package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.game.GetGame;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;

import java.io.IOException;
import java.net.URISyntaxException;

class Game {
    private final Client c;
    Game(Client c) {
        this.c = c;
    }

    ApiResponse<GetGame> GetGame(int gameID) throws IOException, URISyntaxException {
        String strGameID = Integer.toString(gameID);
        Request r = this.c.newRequestBuilder()
                .path("/API/API_GetGame.php")
                .userAgent(this.c.getUserAgent())
                .methodGET()
                .Y(this.c.getWebToken())
                .I(new String[]{strGameID});
        return this.c.Do(r, GetGame.class);
    }
}
