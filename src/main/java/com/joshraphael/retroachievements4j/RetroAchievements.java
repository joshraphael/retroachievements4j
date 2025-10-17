package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.models.connect.StartSession;
import com.joshraphael.retroachievements4j.models.game.GetGame;
import com.joshraphael.retroachievements4j.models.connect.Login;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RetroAchievements {
    ApiResponse<GetGame> GetGame(int gameID) throws IOException, URISyntaxException, InterruptedException;
    ApiResponse<Login> Login(String username, String password) throws IOException, URISyntaxException, InterruptedException;
    ApiResponse<StartSession> StartSession(String username, String token, int gameID) throws IOException, URISyntaxException, InterruptedException;
}