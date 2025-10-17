package com.joshraphael.retroachievements4j.models.http;

public record ApiResponse<T>(int statusCode, T resp) {
}
