package com.joshraphael.retroachievements4j.models.connect;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Login (
    @JsonProperty(value = "Success", required = true)
    Boolean Success,
    @JsonProperty(value = "Status")
    Integer Status,
    @JsonProperty(value = "User")
    String User,
    @JsonProperty(value = "Token")
    String Token,
    @JsonProperty(value = "Score")
    Integer Score,
    @JsonProperty(value = "SoftcoreScore")
    Integer SoftcoreScore,
    @JsonProperty(value = "Messages")
    Integer Messages,
    @JsonProperty(value = "Permissions")
    Integer Permissions,
    @JsonProperty(value = "AccountType")
    String AccountType,
    @JsonProperty(value = "Code")
    String Code,
    @JsonProperty(value = "Error")
    String Error
) {}
