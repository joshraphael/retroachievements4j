import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class RetroAchievementsAPI {
    private String apiKey;

    public RetroAchievementsAPI(String apiKey) {
        this.apiKey = apiKey;
    }

    public String buildURL(String apiKey, String gameId) {
        return "https://retroachievements.org/API/API_GetGame.php?y=" + apiKey + "&i=" + gameId;
    }

    public String makeRequest(String url) {
        // trying to error handle here
        try {
            // create an HTTP client, referred from openjdk.org docs
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
            return null;
        }
    }

    // gets game details by game ID
    public String getGame(String gameId) {
        return makeRequest(buildURL(apiKey, gameId));
    }
}
