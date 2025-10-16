package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.BadHttpResponseException;
import com.joshraphael.retroachievements4j.models.connect.StartSession;
import com.joshraphael.retroachievements4j.models.game.GetGame;
import com.joshraphael.retroachievements4j.models.connect.Login;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RetroAchievements {
    GetGame GetGame(int gameID) throws IOException, URISyntaxException, InterruptedException, BadHttpResponseException;
    Login Login(String username, String password) throws IOException, URISyntaxException, InterruptedException, BadHttpResponseException;
    StartSession StartSession(String username, String token, int gameID) throws IOException, URISyntaxException, InterruptedException, BadHttpResponseException;
}