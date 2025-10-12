package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.BadHttpResponseException;
import com.joshraphael.retroachievements4j.models.game.GetGame;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RetroAchievements {
    GetGame GetGame(int gameID) throws IOException, URISyntaxException, InterruptedException, BadHttpResponseException;
}