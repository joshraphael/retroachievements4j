package com.joshraphael.retroachievements4j.models.connect;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Login {
    @JsonProperty(value = "Success", required = true)
    public boolean Success;
    @JsonProperty(value = "User")
    public String User;
    @JsonProperty(value = "Token")
    public String Token;
    @JsonProperty(value = "Score")
    public int Score;
    @JsonProperty(value = "SoftcoreScore")
    public int SoftcoreScore;
    @JsonProperty(value = "Messages")
    public int Messages;
    @JsonProperty(value = "Permissions")
    public int Permissions;
    @JsonProperty(value = "AccountType")
    public String AccountType;
}
