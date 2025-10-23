package com.joshraphael.retroachievements4j.models.connect;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Ping(
        @JsonProperty(value = "Success", required = true)
        Boolean Success
) {}
