import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Game {

    private String gameId;
    private int lives;
    private int gold;
    private int level;
    private int score;
    private int highScore;
    private int turn;

    private Logger logger = LoggerFactory.getLogger(Game.class);

    public Game() {

    }

    public Game getGame() {
        Request request = new Request();
        Gson gson = new Gson();
        String gameStartUrl = "https://dragonsofmugloar.com/api/v2/game/start";
        String responseForGameStart = null;

        try {
            responseForGameStart = request.POSTRequest(gameStartUrl);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

        return gson.fromJson(responseForGameStart, Game.class);
    }

    public String getGameId() {
        return gameId;
    }

    public int getLives() {
        return lives;
    }

    public int getGold() {
        return gold;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getTurn() {
        return turn;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return "\ngameId = " + gameId + ",\n" +
                "lives = " + lives + ",\n" +
                "gold = " + gold + ",\n" +
                "level = " + level + ",\n" +
                "score = " + score + ",\n" +
                "highScore = " + highScore + ",\n" +
                "turn = " + turn;
    }


}
