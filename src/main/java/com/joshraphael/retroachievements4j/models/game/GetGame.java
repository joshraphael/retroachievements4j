package com.joshraphael.retroachievements4j.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetGame (
    @JsonProperty(value = "Title")
    String Title,
    @JsonProperty(value = "GameTitle")
    String GameTitle,
    @JsonProperty(value = "ConsoleID")
    Integer ConsoleID,
    @JsonProperty(value = "ConsoleName")
    String ConsoleName,
    @JsonProperty(value = "Console")
    String Console,
    @JsonProperty(value = "ForumTopicID")
    Integer ForumTopicID,
    @JsonProperty(value = "Flags")
    Integer Flags,
    @JsonProperty(value = "GameIcon")
    String GameIcon,
    @JsonProperty(value = "ImageIcon")
    String ImageIcon,
    @JsonProperty(value = "ImageTitle")
    String ImageTitle,
    @JsonProperty(value = "ImageIngame")
    String ImageIngame,
    @JsonProperty(value = "ImageBoxArt")
    String ImageBoxArt,
    @JsonProperty(value = "Publisher")
    String Publisher,
    @JsonProperty(value = "Developer")
    String Developer,
    @JsonProperty(value = "Genre")
    String Genre,
    @JsonProperty(value = "Released")
    String Released,
    @JsonProperty(value = "ReleasedAtGranularity")
    String ReleasedAtGranularity
) {}
