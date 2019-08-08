

public class Game {

    private String gameId;
    private int lives;
    private int gold;
    private int level;
    private int score;
    private int highScore;
    private int turn;

    public Game() {

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

    public int getScore() {
        return score;
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