package com.joshraphael.retroachievements4j.models.connect;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AwardAchievement(
        @JsonProperty(value = "Success", required = true)
        Boolean Success,
        @JsonProperty(value = "AchievementsRemaining")
        Integer AchievementsRemaining,
        @JsonProperty(value = "Score")
        Integer Score,
        @JsonProperty(value = "SoftcoreScore")
        Integer SoftcoreScore,
        @JsonProperty(value = "AchievementID")
        Integer AchievementID,
        // Error fields
        @JsonProperty(value = "Status")
        Integer Status,
        @JsonProperty(value = "Code")
        String Code,
        @JsonProperty(value = "Error")
        String Error
) {
}
