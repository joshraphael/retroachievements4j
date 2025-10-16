package com.joshraphael.retroachievements4j.models.connect;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Login (
    @JsonProperty(value = "Success", required = true)
    boolean Success,
    @JsonProperty(value = "Status")
    int Status,
    @JsonProperty(value = "User")
    String User,
    @JsonProperty(value = "Token")
    String Token,
    @JsonProperty(value = "Score")
    int Score,
    @JsonProperty(value = "SoftcoreScore")
    int SoftcoreScore,
    @JsonProperty(value = "Messages")
    int Messages,
    @JsonProperty(value = "Permissions")
    int Permissions,
    @JsonProperty(value = "AccountType")
    String AccountType,
    @JsonProperty(value = "Code")
    String Code,
    @JsonProperty(value = "Error")
    String Error
) {}
