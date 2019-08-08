import Response.PurchasedItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Shopping {

    Request request = new Request();
    Gson gson = new Gson();

    public void start(Game game) throws IOException {
        List<Item> items = getItems(game.getGameId());

        for (Item item : items) {
            boolean isEnoughGoldForPurchase = item.getCost() < game.getGold();

            if (isEnoughGoldForPurchase) {
                String shopItemUrl = "https://dragonsofmugloar.com/api/v2/" + game.getGameId() + "/shop/buy/" + item.getId();
                String purchaseResponse = request.POSTRequest(shopItemUrl);
                PurchasedItem purchasedItem = gson.fromJson(purchaseResponse, PurchasedItem.class);
                game.setGold(purchasedItem.getGold());
                game.setLives(purchasedItem.getLives());
                game.setLevel(purchasedItem.getLevel());
            }
        }
    }

    private List<Item> getItems(String gameId) throws IOException {
        String shopUrl = "https://dragonsofmugloar.com/api/v2/" + gameId + "/shop";
        String allItemsInShop = request.GETRequest(shopUrl);
        Type listItems = new TypeToken<ArrayList<Item>>() {
        }.getType();
        return new Gson().fromJson(allItemsInShop, listItems);
    }
}