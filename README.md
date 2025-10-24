# <p align="center">retroachievements4j</p>

<p align="center">
    <i>A Java library that lets you get achievement, user, game data and more from RetroAchievements.</i>
</p>
<p align="center">
    <a href="https://api-docs.retroachievements.org/getting-started.html"><strong>Documentation: Get Started</strong></a>
</p>
<br>
<hr />

[![GitHub License](https://img.shields.io/github/license/joshraphael/retroachievements4j)](https://github.com/joshraphael/retroachievements4j/blob/main/LICENSE)
[![javadoc](https://img.shields.io/github/v/tag/joshraphael/retroachievements4j?logo=openjdk&label=javadoc.io)](https://javadoc.io/doc/com.joshraphael/retroachievements4j)
[![pipeline](https://github.com/joshraphael/retroachievements4j/actions/workflows/test.yaml/badge.svg)](https://github.com/joshraphael/retroachievements4j/actions/workflows/test.yaml)
[![pipeline](https://github.com/joshraphael/retroachievements4j/actions/workflows/release.yaml/badge.svg)](https://github.com/joshraphael/retroachievements4j/actions/workflows/release.yaml)
[![GitHub Tag](https://img.shields.io/github/v/tag/joshraphael/retroachievements4j)](https://github.com/joshraphael/retroachievements4j/tags)
[![GitHub repo size](https://img.shields.io/github/repo-size/joshraphael/retroachievements4j)](https://github.com/joshraphael/retroachievements4j/archive/main.zip)

Available on [Maven Central](https://central.sonatype.com/artifact/com.joshraphael/retroachievements4j) and [GitHub](https://github.com/joshraphael/retroachievements4j/packages/2698688)

## Installation

include the dependency in your pom.xml or build.gradle file

```xml
<dependency>
    <groupId>com.joshraphael</groupId>
    <artifactId>retroachievements4j</artifactId>
    <version>X.Y.Z</version>
</dependency>
```

## Usage

Construct a client in your java code giving the hostname and user agent to talk with the API.

```java
CloseableHttpClient httpClient = HttpClients.createDefault();
RetroAchievementsClient c = new RetroAchievementsClient(httpClient, RetroAchievementsClient.URL, "my_app/v0.0.0");
```

## API
For convenience, the API docs can be found in the tables below

<h3>Game</h3>

| Function            | Description                         | Links                                                                    |
|---------------------|-------------------------------------|--------------------------------------------------------------------------|
| `GetGame()`         | Get basic metadata about a game.    | [docs](https://api-docs.retroachievements.org/v1/get-game.html)          |
| `GetGameExtended()` | Get extended metadata about a game. | [docs](https://api-docs.retroachievements.org/v1/get-game-extended.html) |