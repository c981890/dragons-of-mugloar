import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Logger logger = LoggerFactory.getLogger(Main.class);
        Game game = new Game().getGame();


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
