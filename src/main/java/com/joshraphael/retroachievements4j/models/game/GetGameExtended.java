package com.joshraphael.retroachievements4j.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Map;

public record GetGameExtended(
        @JsonProperty(value = "ID")
        Integer ID,
        @JsonProperty(value = "Title")
        String Title,
        @JsonProperty(value = "ConsoleID")
        Integer ConsoleID,
        @JsonProperty(value = "ForumTopicID")
        Integer ForumTopicID,
        @JsonProperty(value = "Flags")
        Integer Flags,
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
        String ReleasedAtGranularity,
        @JsonProperty(value = "IsFinal")
        Boolean IsFinal,
        @JsonProperty(value = "RichPresencePatch")
        String RichPresencePatch,
        @JsonProperty(value = "GuideURL")
        String GuideURL,
        @JsonProperty(value = "Updated")
        String Updated,
        @JsonProperty(value = "ConsoleName")
        String ConsoleName,
        @JsonProperty(value = "ParentGameID")
        Integer ParentGameID,
        @JsonProperty(value = "NumDistinctPlayers")
        Integer NumDistinctPlayers,
        @JsonProperty(value = "NumAchievements")
        Integer NumAchievements,
        @JsonProperty(value = "Achievements")
        Map<String, GetGameExtendedAchievement> Achievements,
        @JsonProperty(value = "Claims")
        ArrayList<GetGameExtendedClaim> Claims,
        @JsonProperty(value = "NumDistinctPlayersCasual")
        Integer NumDistinctPlayersCasual,
        @JsonProperty(value = "NumDistinctPlayersHardcore")
        Integer NumDistinctPlayersHardcore
) {}
