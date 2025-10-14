package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.BadHttpResponseException;
import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.connect.Login;

import java.io.IOException;
import java.net.URISyntaxException;

class Connect {
    private final Client c;
    Connect(Client c) {
        this.c = c;
    }

    Login Login(String username, String password) throws IOException, URISyntaxException, InterruptedException, BadHttpResponseException {
        Request r = this.c.newRequestBuilder()
                .path("/dorequest.php")
                .userAgent(this.c.getUserAgent())
                .methodPOST()
                .U(username)
                .P(password)
                .R("login2");
        return this.c.Do(r, Login.class);
    }
}
