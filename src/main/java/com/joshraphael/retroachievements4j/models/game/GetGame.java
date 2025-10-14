package com.joshraphael.retroachievements4j.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetGame {
    @JsonProperty("Title")
    public String Title;
    @JsonProperty("GameTitle")
    public String GameTitle;
    @JsonProperty("ConsoleID")
    public int ConsoleID;
    @JsonProperty("ConsoleName")
    public String ConsoleName;
    @JsonProperty("Console")
    public String Console;
    @JsonProperty("ForumTopicID")
    public int ForumTopicID;
    @JsonProperty("Flags")
    public int Flags;
    @JsonProperty("GameIcon")
    public String GameIcon;
    @JsonProperty("ImageIcon")
    public String ImageIcon;
    @JsonProperty("ImageTitle")
    public String ImageTitle;
    @JsonProperty("ImageIngame")
    public String ImageIngame;
    @JsonProperty("ImageBoxArt")
    public String ImageBoxArt;
    @JsonProperty("Publisher")
    public String Publisher;
    @JsonProperty("Developer")
    public String Developer;
    @JsonProperty("Genre")
    public String Genre;
    @JsonProperty("Released")
    public String Released;
    @JsonProperty("ReleasedAtGranularity")
    public String ReleasedAtGranularity;
}
