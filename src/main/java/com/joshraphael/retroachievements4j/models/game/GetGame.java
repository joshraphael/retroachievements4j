package com.joshraphael.retroachievements4j.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetGame {
    @JsonProperty(value = "Title")
    public String Title;
    @JsonProperty(value = "GameTitle")
    public String GameTitle;
    @JsonProperty(value = "ConsoleID")
    public int ConsoleID;
    @JsonProperty(value = "ConsoleName")
    public String ConsoleName;
    @JsonProperty(value = "Console")
    public String Console;
    @JsonProperty(value = "ForumTopicID")
    public int ForumTopicID;
    @JsonProperty(value = "Flags")
    public int Flags;
    @JsonProperty(value = "GameIcon")
    public String GameIcon;
    @JsonProperty(value = "ImageIcon")
    public String ImageIcon;
    @JsonProperty(value = "ImageTitle")
    public String ImageTitle;
    @JsonProperty(value = "ImageIngame")
    public String ImageIngame;
    @JsonProperty(value = "ImageBoxArt")
    public String ImageBoxArt;
    @JsonProperty(value = "Publisher")
    public String Publisher;
    @JsonProperty(value = "Developer")
    public String Developer;
    @JsonProperty(value = "Genre")
    public String Genre;
    @JsonProperty(value = "Released")
    public String Released;
    @JsonProperty(value = "ReleasedAtGranularity")
    public String ReleasedAtGranularity;
}
