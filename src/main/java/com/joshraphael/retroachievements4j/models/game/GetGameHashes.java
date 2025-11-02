package com.joshraphael.retroachievements4j.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joshraphael.retroachievements4j.models.Error;

public record GetGameHashes(
        @JsonProperty(value = "Results")
        GetGameHashesResult[] Results,
        // Error
        @JsonProperty(value = "message")
        String Message,
        @JsonProperty(value = "errors")
        Error[] Errors
) {}