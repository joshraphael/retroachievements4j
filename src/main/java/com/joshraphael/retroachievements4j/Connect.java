package com.joshraphael.retroachievements4j;

import com.joshraphael.retroachievements4j.http.Request;
import com.joshraphael.retroachievements4j.models.connect.AwardAchievement;
import com.joshraphael.retroachievements4j.models.connect.Login;
import com.joshraphael.retroachievements4j.models.connect.Ping;
import com.joshraphael.retroachievements4j.models.connect.StartSession;
import com.joshraphael.retroachievements4j.models.http.ApiResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    ApiResponse<AwardAchievement> AwardAchievement(String username, String token, String targetUsername, int achievementID, boolean hardcore) throws IOException, URISyntaxException, NoSuchAlgorithmException {
        StringBuilder hashString = new StringBuilder();
        hashString.append(achievementID);
        hashString.append(username);
        int hc = 0;
        if(hardcore) {
            hc = 1;
        }
        hashString.append(hc);
        Request r = this.c.newRequestBuilder()
                .path("/dorequest.php")
                .userAgent(this.c.getUserAgent())
                .methodPOST()
                .U(username)
                .T(token)
                .R("awardachievement")
                .A(achievementID)
                .H(hardcore);
        if(targetUsername != null && !targetUsername.isBlank()) {
            hashString = new StringBuilder();
            hashString.append(achievementID);
            hashString.append(targetUsername);
            hashString.append(hc);
            hashString.append(achievementID);
            r = r.K(targetUsername);
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theMD5digest = md.digest(hashString.toString().getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : theMD5digest) {
            hexString.append(String.format("%02x", b));
        }
        String md5 = hexString.toString();
        r.V(md5);
        return this.c.Do(r, AwardAchievement.class);
    }
}
