package com.joshraphael.retroachievements4j.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetGame {
    @JsonProperty("Title")
    public String Title;
    @JsonProperty("GameTitle")
    public String GameTitle;
}
