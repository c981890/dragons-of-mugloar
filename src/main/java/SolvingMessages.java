import Response.SolvedMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SolvingMessages {

    private Logger logger = LoggerFactory.getLogger(SolvingMessages.class);
    private Request request = new Request();
    private Gson gson = new Gson();

    public void start(Game game) throws IOException {

        List<Message> allMessagesForSolving = getMessages(game.getGameId());

        int countOfTurnsPerMessages = 1;
        for (Message ad : allMessagesForSolving) {
            boolean isMessageSolvingProbabilityAcceptable =
                    countOfTurnsPerMessages <= ad.getExpiresIn() &&
                    (ad.getProbability().equals("Walk in the park") ||
                    ad.getProbability().equals("Piece of cake") ||
                    ad.getProbability().equals("Sure thing"));

            if (isMessageSolvingProbabilityAcceptable) {
                SolvedMessage solvedMessage = getSolvedMessage(game.getGameId(), ad.getAdId());

                if (solvedMessage.getLives() > 0) {
                    setNewGameStatistics(game, solvedMessage);
                } else {
                    setNewGameStatistics(game, solvedMessage);
                    break;
                }
                countOfTurnsPerMessages++;
            }
        }
        boolean isPreviouslyNoMessagesSolved = (countOfTurnsPerMessages == 1);

        if (isPreviouslyNoMessagesSolved) {
            for (int i = 0; i < 1; i++) {
                SolvedMessage responseForSolveMessage = getSolvedMessage(game.getGameId(), allMessagesForSolving.get(i).getAdId());
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
    }

    SolvedMessage getSolvedMessage(String gameId, String adId) throws IOException {
        String messageResponse = "https://dragonsofmugloar.com/api/v2/" + gameId + "/solve/" + adId;
        String solveResponse = request.POSTRequest(messageResponse);
        return gson.fromJson(solveResponse, SolvedMessage.class);
    }

    List<Message> getMessages(String gameId) throws IOException {
        String messagesUrl = "https://dragonsofmugloar.com/api/v2/" + gameId + "/messages";
        String allMessages = request.GETRequest(messagesUrl);
        Type listMessages = new TypeToken<ArrayList<Message>>() {
        }.getType();
        return new Gson().fromJson(allMessages, listMessages);
    }

    void setNewGameStatistics(Game game, SolvedMessage responseForSolveMessage) {
        game.setGold(responseForSolveMessage.getGold());
        game.setLives(responseForSolveMessage.getLives());
        game.setScore(responseForSolveMessage.getScore());
        game.setHighScore(responseForSolveMessage.getHighScore());
        game.setTurn(responseForSolveMessage.getTurn());
    }
}
