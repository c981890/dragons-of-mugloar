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
