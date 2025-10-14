package com.joshraphael.retroachievements4j.models.connect;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StartSession {
    @JsonProperty(value = "Success", required = true)
    public boolean Success;
}
