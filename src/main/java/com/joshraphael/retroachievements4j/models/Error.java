package com.joshraphael.retroachievements4j.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Error (
        @JsonProperty(value = "status")
        String Status,
        @JsonProperty(value = "code")
        String Code,
        @JsonProperty(value = "title")
        String Title
) {}
