package com.joshraphael.retroachievements4j.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetGameExtendedAchievement(
        @JsonProperty(value = "ID")
        int ID,
        @JsonProperty(value = "NumAwarded")
        int NumAwarded,
        @JsonProperty(value = "NumAwardedHardcore")
        int NumAwardedHardcore,
        @JsonProperty(value = "Title")
        String Title,
        @JsonProperty(value = "Description")
        String Description,
        @JsonProperty(value = "Points")
        int Points,
        @JsonProperty(value = "TrueRatio")
        int TrueRatio,
        @JsonProperty(value = "Author")
        String Author,
        @JsonProperty(value = "AuthorULID")
        String AuthorULID,
        @JsonProperty(value = "DateModified")
        String DateModified,
        @JsonProperty(value = "DateCreated")
        String DateCreated,
        @JsonProperty(value = "BadgeName")
        String BadgeName,
        @JsonProperty(value = "DisplayOrder")
        int DisplayOrder,
        @JsonProperty(value = "MemAddr")
        String MemAddr,
        @JsonProperty(value = "type")
        String Type
) {}
