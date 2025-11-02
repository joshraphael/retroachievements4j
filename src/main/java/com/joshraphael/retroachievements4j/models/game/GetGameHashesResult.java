package com.joshraphael.retroachievements4j.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetGameHashesResult(
        @JsonProperty(value = "Name")
        String Name,
        @JsonProperty(value = "MD5")
        String MD5,
        @JsonProperty(value = "Labels")
        String[] Labels,
        @JsonProperty(value = "PatchUrl")
        String PatchUrl
) {}
