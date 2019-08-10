package Response;

public class PurchasedItem {

    private String shoppingSuccess;
    private int gold;
    private int lives;
    private int level;
    private int turn;

    public PurchasedItem(String shoppingSuccess, int gold, int lives, int level, int turn) {
        this.shoppingSuccess = shoppingSuccess;
        this.gold = gold;
        this.lives = lives;
        this.level = level;
        this.turn = turn;
    }

    public PurchasedItem() {
    }

    public int getGold() {
        return gold;
    }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
    }

    public int getTurn() {
        return turn;
    }
}
