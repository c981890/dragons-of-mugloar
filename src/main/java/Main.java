

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
        logger.info("Hello World");
        Request request = new Request();
        Gson gson = new Gson();
        String gameStartUrl = "https://dragonsofmugloar.com/api/v2/game/start";
        String responseForGameStart = request.POSTRequest(gameStartUrl);
        Game game = gson.fromJson(responseForGameStart, Game.class);


        while (game.getLives() > 0) {
            String messagesUrl = "https://dragonsofmugloar.com/api/v2/" + game.getGameId() + "/messages";
            String allMessages = request.GETRequest(messagesUrl);
            Type listMessages = new TypeToken<ArrayList<Message>>() {
            }.getType();
            List<Message> messages = new Gson().fromJson(allMessages, listMessages);

            int countOfTurnsPerMessages = 1;
            for (Message ad : messages) {
                if (countOfTurnsPerMessages <= ad.getExpiresIn() && (ad.getProbability().equals("Walk in the park") ||
                        ad.getProbability().equals("Piece of cake") ||
                        ad.getProbability().equals("Sure thing") )) {
                    String messageResponse = "https://dragonsofmugloar.com/api/v2/" + game.getGameId() + "/solve/" + ad.getAdId();
                    String solveResponse = request.POSTRequest(messageResponse);
                    SolvedMessage responseForSolveMessage = gson.fromJson(solveResponse, SolvedMessage.class);
                    if (responseForSolveMessage.getLives() > 0) {
                        setNewGameStatistics(game, responseForSolveMessage);
                    } else {
                        setNewGameStatistics(game, responseForSolveMessage);
                        break;
                    }
                    countOfTurnsPerMessages++;
                }
            }
            if (countOfTurnsPerMessages == 1) {
                for (int i = 0; i < 1; i++) {
                    String messageResponse = "https://dragonsofmugloar.com/api/v2/" + game.getGameId() + "/solve/" + messages.get(i).getAdId();
                    String solveResponse = request.POSTRequest(messageResponse);
                    SolvedMessage responseForSolveMessage = gson.fromJson(solveResponse, SolvedMessage.class);
                    logger.debug(responseForSolveMessage.toString());
                    if (responseForSolveMessage.getLives() > 0) {
                        setNewGameStatistics(game, responseForSolveMessage);
                    } else {
                        setNewGameStatistics(game, responseForSolveMessage);
                        break;
                    }
                    countOfTurnsPerMessages++;
                }
            }

            if (game.getLives() == 0) break;

            String shopUrl = "https://dragonsofmugloar.com/api/v2/" + game.getGameId() + "/shop";
            String allItemsInShop = request.GETRequest(shopUrl);
            Type listItems = new TypeToken<ArrayList<Item>>() {
            }.getType();
            List<Item> items = new Gson().fromJson(allItemsInShop, listItems);

            for (Item item : items) {
                if (item.getCost() < game.getGold()) {
                    String shopItemUrl = "https://dragonsofmugloar.com/api/v2/" + game.getGameId() + "/shop/buy/" + item.getId();
                    String purchaseResponse = request.POSTRequest(shopItemUrl);
                    PurchasedItem purchasedItem = gson.fromJson(purchaseResponse, PurchasedItem.class);
                    game.setGold(purchasedItem.getGold());
                    game.setLives(purchasedItem.getLives());
                    game.setLevel(purchasedItem.getLevel());
                }
            }
        }

        logger.info(game.toString());

    }

    private static void setNewGameStatistics(Game game, SolvedMessage responseForSolveMessage) {
        game.setGold(responseForSolveMessage.getGold());
        game.setLives(responseForSolveMessage.getLives());
        game.setScore(responseForSolveMessage.getScore());
        game.setHighScore(responseForSolveMessage.getHighScore());
        game.setTurn(responseForSolveMessage.getTurn());
    }


}
