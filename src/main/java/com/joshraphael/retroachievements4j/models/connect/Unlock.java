package com.joshraphael.retroachievements4j.models.connect;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Unlock(
        @JsonProperty(value = "ID", required = true)
        Integer ID,
        @JsonProperty(value = "When", required = true)
        Long When
) {}
