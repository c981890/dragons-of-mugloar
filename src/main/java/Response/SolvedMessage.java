package Response;

public class SolvedMessage {
    private boolean success;
    private int lives;
    private int gold;
    private int score;
    private int highScore;
    private int turn;
    private String message;

    public SolvedMessage() {
    }

    public SolvedMessage(boolean success, int lives, int gold, int score, int highScore, int turn, String message) {
        this.success = success;
        this.lives = lives;
        this.gold = gold;
        this.score = score;
        this.highScore = highScore;
        this.turn = turn;
        this.message = message;
    }

    public int getGold() {
        return gold;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getTurn() {
        return turn;
    }
}
