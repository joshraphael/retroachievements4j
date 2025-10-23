package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.models.connect.AwardAchievement;
import com.joshraphael.retroachievements4j.models.connect.Ping;
import com.joshraphael.retroachievements4j.models.connect.StartSession;
import com.joshraphael.retroachievements4j.models.game.GetGame;
import com.joshraphael.retroachievements4j.models.connect.Login;
import com.joshraphael.retroachievements4j.models.game.GetGameExtended;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

public interface IRetroAchievements4j {
    ApiResponse<GetGame> GetGame(String webToken, int gameID) throws IOException, URISyntaxException;
    ApiResponse<GetGameExtended> GetGameExtended(String webToken, int gameID, Boolean unofficial) throws IOException, URISyntaxException;
    ApiResponse<Login> Login(String username, String password) throws IOException, URISyntaxException;
    ApiResponse<StartSession> StartSession(String username, String token, int gameID, String targetUsername) throws IOException, URISyntaxException;
    ApiResponse<Ping> Ping(String username, String token, int gameID, String targetUsername, String richPresence) throws IOException, URISyntaxException;
    ApiResponse<AwardAchievement> AwardAchievement(String username, String token, String targetUsername, int achievementID, boolean hardcore) throws IOException, URISyntaxException, NoSuchAlgorithmException;
}