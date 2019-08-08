
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Logger logger = LoggerFactory.getLogger(Main.class);
        Game game = getGame();


        while (game.getLives() > 0) {
            SolvingMessages solvingMessages = new SolvingMessages();
            solvingMessages.start(game);

            if (game.getLives() == 0) break;

            Shopping shopping = new Shopping();
            shopping.start(game);
        }

        logger.info(game.toString());

    }

    private static Game getGame() throws IOException {
        Request request = new Request();
        Gson gson = new Gson();
        String gameStartUrl = "https://dragonsofmugloar.com/api/v2/game/start";
        String responseForGameStart = request.POSTRequest(gameStartUrl);
        return gson.fromJson(responseForGameStart, Game.class);
    }
}
