package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.connect.Login;
import com.joshraphael.retroachievements4j.models.connect.Ping;
import com.joshraphael.retroachievements4j.models.connect.StartSession;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;

import java.io.IOException;
import java.net.URISyntaxException;

class Connect {
    private final RetroAchievementsClient c;
    Connect(RetroAchievementsClient c) {
        this.c = c;
    }

    ApiResponse<Login> Login(String username, String password) throws IOException, URISyntaxException {
        Request r = this.c.newRequestBuilder()
                .path("/dorequest.php")
                .userAgent(this.c.getUserAgent())
                .methodPOST()
                .U(username)
                .P(password)
                .R("login2");
        return this.c.Do(r, Login.class);
    }

    ApiResponse<StartSession> StartSession(String username, String token, int gameID, String targetUsername) throws IOException, URISyntaxException {
        Request r = this.c.newRequestBuilder()
                .path("/dorequest.php")
                .userAgent(this.c.getUserAgent())
                .methodPOST()
                .U(username)
                .T(token)
                .R("startsession")
                .G(gameID);
        if(targetUsername != null && !targetUsername.isBlank()) {
            r = r.K(targetUsername);
        }
        return this.c.Do(r, StartSession.class);
    }

    ApiResponse<Ping> Ping(String username, String token, int gameID, String targetUsername, String richPresence) throws IOException, URISyntaxException {
        Request r = this.c.newRequestBuilder()
                .path("/dorequest.php")
                .userAgent(this.c.getUserAgent())
                .methodPOST()
                .U(username)
                .T(token)
                .R("ping")
                .G(gameID);
        if(targetUsername != null && !targetUsername.isBlank()) {
            r = r.K(targetUsername);
        }
        if(richPresence != null && !richPresence.isBlank()) {
            r = r.formPart("m", richPresence);
        }
        return this.c.Do(r, Ping.class);
    }
}
