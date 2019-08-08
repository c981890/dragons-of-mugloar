

import Response.SolvedMessage;
import Response.PurchasedItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException {

        Logger logger = LoggerFactory.getLogger(Main.class);
        Request request = new Request();
        Gson gson = new Gson();
        String gameStartUrl = "https://dragonsofmugloar.com/api/v2/game/start";
        String responseForGameStart = request.POSTRequest(gameStartUrl);
        Game game = gson.fromJson(responseForGameStart, Game.class);


        while (game.getLives() > 0) {
            SolvingMessages solvingMessages = new SolvingMessages();
            solvingMessages.start(game);

            if (game.getLives() == 0) break;

            Shopping shopping = new Shopping();
            shopping.start(game);
        }

        logger.info(game.toString());

    }
}
