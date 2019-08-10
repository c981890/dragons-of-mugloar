import Response.PurchasedItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Shopping {

    private Request request = new Request();
    private Gson gson = new Gson();

    public void start(Game game) throws IOException {
        List<Item> itemsPossibleToPurchase = getItems(game.getGameId());

        for (Item item : itemsPossibleToPurchase) {
            boolean isEnoughGoldForPurchase = item.getCost() < game.getGold();

            if (isEnoughGoldForPurchase) {
                PurchasedItem purchasedItem = getPurchasedItem(game.getGameId(), item.getId());
                setNewGameStatistics(game, purchasedItem);
            }
        }
    }

    void setNewGameStatistics(Game game, PurchasedItem purchasedItem) {
        game.setGold(purchasedItem.getGold());
        game.setLives(purchasedItem.getLives());
        game.setLevel(purchasedItem.getLevel());
        game.setTurn(purchasedItem.getTurn());
    }

    PurchasedItem getPurchasedItem(String gameId, String itemId) throws IOException {
        String shopItemUrl = "https://dragonsofmugloar.com/api/v2/" + gameId + "/shop/buy/" + itemId;
        String purchaseResponse = request.POSTRequest(shopItemUrl);
        return gson.fromJson(purchaseResponse, PurchasedItem.class);
    }

    List<Item> getItems(String gameId) throws IOException {
        String shopUrl = "https://dragonsofmugloar.com/api/v2/" + gameId + "/shop";
        String allItemsInShop = request.GETRequest(shopUrl);
        Type listItems = new TypeToken<ArrayList<Item>>() {
        }.getType();
        return new Gson().fromJson(allItemsInShop, listItems);
    }
}
