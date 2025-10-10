package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.models.game.GetGame;

public interface RetroAchievements {
    GetGame GetGame(int gameID);
}