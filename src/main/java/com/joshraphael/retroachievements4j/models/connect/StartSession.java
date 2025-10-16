package com.joshraphael.retroachievements4j.models.connect;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public record StartSession (
    @JsonProperty(value = "Success", required = true)
    boolean Success,
    @JsonProperty(value = "Status")
    int Status,
    @JsonProperty(value = "HardcoreUnlocks")
    ArrayList<Unlock> HardcoreUnlocks,
    @JsonProperty(value = "Unlocks")
    ArrayList<Unlock> Unlocks,
    @JsonProperty(value = "ServerNow")
    long ServerNow,
    @JsonProperty(value = "Code")
    String Code,
    @JsonProperty(value = "Error")
    String Error
) {}

