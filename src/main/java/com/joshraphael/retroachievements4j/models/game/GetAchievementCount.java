package com.joshraphael.retroachievements4j.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joshraphael.retroachievements4j.models.Error;

public record GetAchievementCount(
        @JsonProperty(value = "GameID")
        Integer GameID,
        @JsonProperty(value = "AchievementIDs")
        Integer[] AchievementIDs,
        // Error
        @JsonProperty(value = "message")
        String Message,
        @JsonProperty(value = "errors")
        Error[] Errors
) {}
