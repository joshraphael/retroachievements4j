package com.joshraphael.retroachievements4j.models.connect;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public record StartSession (
    @JsonProperty(value = "Success", required = true)
    Boolean Success,
    @JsonProperty(value = "HardcoreUnlocks")
    ArrayList<Unlock> HardcoreUnlocks,
    @JsonProperty(value = "Unlocks")
    ArrayList<Unlock> Unlocks,
    @JsonProperty(value = "ServerNow")
    Long ServerNow,
    // Error fields
    @JsonProperty(value = "Status")
    Integer Status,
    @JsonProperty(value = "Code")
    String Code,
    @JsonProperty(value = "Error")
    String Error
) {}

