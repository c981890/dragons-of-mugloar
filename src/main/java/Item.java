public class Item {

    private String id;
    private String name;
    private int cost;

    public Item(String id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }
}
