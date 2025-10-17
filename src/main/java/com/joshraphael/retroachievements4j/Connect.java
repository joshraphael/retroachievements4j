package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.connect.Login;
import com.joshraphael.retroachievements4j.models.connect.StartSession;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;

import java.io.IOException;
import java.net.URISyntaxException;

class Connect {
    private final Client c;
    Connect(Client c) {
        this.c = c;
    }

    ApiResponse<Login> Login(String username, String password) throws IOException, URISyntaxException, InterruptedException {
        Request r = this.c.newRequestBuilder()
                .path("/dorequest.php")
                .userAgent(this.c.getUserAgent())
                .methodPOST()
                .U(username)
                .P(password)
                .R("login2");
        return this.c.Do(r, Login.class);
    }

    ApiResponse<StartSession> StartSession(String username, String token, int gameID) throws IOException, URISyntaxException, InterruptedException {
        Request r = this.c.newRequestBuilder()
                .path("/dorequest.php")
                .userAgent(this.c.getUserAgent())
                .methodPOST()
                .U(username)
                .T(token)
                .R("startsession")
                .G(gameID);
        return this.c.Do(r, StartSession.class);
    }
}
