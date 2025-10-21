package com.joshraphael.retroachievements4j.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetGameExtendedAchievement(
        @JsonProperty(value = "ID")
        Integer ID,
        @JsonProperty(value = "NumAwarded")
        Integer NumAwarded,
        @JsonProperty(value = "NumAwardedHardcore")
        Integer NumAwardedHardcore,
        @JsonProperty(value = "Title")
        String Title,
        @JsonProperty(value = "Description")
        String Description,
        @JsonProperty(value = "Points")
        Integer Points,
        @JsonProperty(value = "TrueRatio")
        Integer TrueRatio,
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
        Integer DisplayOrder,
        @JsonProperty(value = "MemAddr")
        String MemAddr,
        @JsonProperty(value = "type")
        String Type
) {}
