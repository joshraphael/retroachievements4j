package com.joshraphael.retroachievements4j.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetGameExtendedClaim(
        @JsonProperty(value = "User")
        String User,
        @JsonProperty(value = "ULID")
        String ULID,
        @JsonProperty(value = "SetType")
        int SetType,
        @JsonProperty(value = "ClaimType")
        int ClaimType,
        @JsonProperty(value = "Created")
        String Created,
        @JsonProperty(value = "Expiration")
        String Expiration
) {}
