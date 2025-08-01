public class Main {
    public static void main(String[] args) {
        // this is Kevin's API key, should store this in .env in future?
        RetroAchievementsAPI api = new RetroAchievementsAPI("J0xQ2x5NSxhaQkBWTThKwJaMrypFqJ7e");
        // mgs1 game ID
        String gameId = "11244";

        String response = api.getGame(gameId);
        if (response != null) {
            System.out.println("Response: " + response);
        } else {
            System.out.println("Failed to retrieve data.");
        }

    };
};
